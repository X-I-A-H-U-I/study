package com.github.xia.security.gate.filter;

import com.github.xia.security.api.feign.ClientLogFeignClient;
import com.github.xia.security.api.feign.PermissionFeignClient;
import com.github.xia.security.api.feign.UserFeignClient;
import com.github.xia.security.common.util.ClientUtil;
import com.github.xia.security.common.vo.LogInfo;
import com.github.xia.security.common.vo.PermissionInfo;
import com.github.xia.security.common.vo.UserInfo;
import com.github.xia.security.gate.thread.LogThread;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>自定义过滤器继承路由过滤</p>
 *
 * @author: xia
 * @date: 2020-03-14
 * @since: JDK 1.8
 * @version: 1.0
 */
//@Configuration
public class SessionAccessFilter extends ZuulFilter {

    private final Logger log = LoggerFactory.getLogger(SessionAccessFilter.class);

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private PermissionFeignClient permissionFeignClient;

    @Autowired
    private ClientLogFeignClient clientLogFeignClient;

    /**
     * IP黑名单
     */
    private List<String> blackIpList = Arrays.asList("8.8.8.8");

    @Value("${gate.ignore.startWith}")
    private String startWith;

    @Value("${gate.ignore.contain}")
    private String contain;

    /**
     * to classify a filter by type. Standard types in Zuul are "pre" for pre-routing filtering,
     * "route" for routing to an origin, "post" for post-routing filters, "error" for error handling.
     * We also support a "static" type for static responses see  StaticResponseFilter.
     * Any filterType made be created or added and run by calling FilterProcessor.runFilters(type)
     *
     * @return A String representing that type
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * filterOrder() must also be defined for a filter. Filters may have the same  filterOrder if precedence is not
     * important for a filter. filterOrders do not need to be sequential.
     *
     * @return the int order of a filter
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * a "true" return from this method means that the run() method should be invoked
     *
     * @return true if the run() method should be invoked. false will not invoke the run() method
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * if shouldFilter() is true, this method will be invoked. this method is the core method of a ZuulFilter
     *
     * @return Some arbitrary artifact may be returned. Current implementation ignores it.
     * @throws ZuulException if an error occurs during execution.
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpSession httpSession = ctx.getRequest().getSession();
        SecurityContextImpl securityContextImpl =
                (SecurityContextImpl) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        User user = (User) securityContextImpl.getAuthentication().getPrincipal();
        HttpServletRequest request = ctx.getRequest();
        final String requestUri = request.getRequestURI();
        final String method = request.getMethod();
        // 不进行拦截的地址
        if (isStartWith(requestUri) || isContains(requestUri)) {
            return null;
        }
        log.debug("uri：" + requestUri + "----method：" + method);
        String username = user.getUsername();
        List<PermissionInfo> permissionInfos = getPermissionInfos(request, username);
        // 查找合法链接
        checkAllow(requestUri,method,ctx,username);
        // 设置头部校验信息
        ctx.addZuulRequestHeader("Authorization",
                Base64Utils.encodeToString(user.getUsername().getBytes()));
        return null;
    }

    private void setCurrentUserInfoAndLog(RequestContext ctx, String username, PermissionInfo pm) {
        UserInfo info = userFeignClient.getUserByUsername(username);
        String host =  ClientUtil.getClientIp(ctx.getRequest());
        ctx.addZuulRequestHeader("userId", info.getId());
        ctx.addZuulRequestHeader("userName", info.getName());
        ctx.addZuulRequestHeader("userHost", ClientUtil.getClientIp(ctx.getRequest()));
        LogInfo logInfo = new LogInfo(pm.getMenu(),pm.getName(),pm.getUri(),new Date(),info.getId(),info.getName(),host);
        LogThread.getInstance().offerQueue(logInfo);
    }

    private List<PermissionInfo> getPermissionInfos(HttpServletRequest request, String username) {
        List<PermissionInfo> permissionInfos;
        if (request.getSession().getAttribute("permission") == null) {
            permissionInfos = permissionFeignClient.getPermissionByUsername(username);
            request.getSession().setAttribute("permission", permissionInfos);
        } else {
            permissionInfos = (List<PermissionInfo>) request.getSession().getAttribute("permission");
        }
        return permissionInfos;
    }

    /**
     * 权限校验
     *
     * @param requestUri
     * @param method
     * @param ctx
     */
    private void checkAllow(final String requestUri, final String method ,RequestContext ctx,String username)  {
        log.debug("uri：" + requestUri + "----method：" + method);
        List<PermissionInfo> permissionInfos = getPermissionInfos(ctx.getRequest(), username) ;
        Collection<PermissionInfo> result =
                Collections2.filter(permissionInfos, new Predicate<PermissionInfo>() {
                    @Override
                    public boolean apply(PermissionInfo permissionInfo) {
                        String url = permissionInfo.getUri();
                        String uri = url.replaceAll("\\{\\*\\}", "[a-zA-Z\\\\d]+");
                        String regEx = "^" + uri + "$";
                        return (Pattern.compile(regEx).matcher(requestUri).find() || requestUri.startsWith(url + "/"))
                                && method.equals(permissionInfo.getMethod());
                    }
                });
        if (result.size() <= 0) {
            setFailedRequest("403 Forbidden!", 403);
        } else{
            PermissionInfo[] pms =  result.toArray(new PermissionInfo[]{});
            PermissionInfo pm = pms[0];
            if(!method.equals("GET")){
                setCurrentUserInfoAndLog(ctx, username, pm);
            }
        }
    }

    /**
     * 是否包含某种特征
     *
     * @param requestUri
     * @return
     */
    private boolean isContains(String requestUri) {
        boolean flag = false;
        for (String s : contain.split(",")) {
            if (requestUri.contains(s)) {
                return true;
            }
        }
        return flag;
    }

    /**
     * 是否URL开头
     *
     * @param requestUri
     * @return
     */
    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : startWith.split(",")) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }

    /**
     * Reports an error message given a response body and code.
     *
     * @param body
     * @param code
     */
    private void setFailedRequest(String body, int code) {
        log.debug("Reporting error ({}): {}", code, body);
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
            throw new RuntimeException("Code: " + code + ", " + body);
        }
    }
}
