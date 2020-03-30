package com.github.xia.security.admin.rpc;

import com.github.xia.security.admin.biz.ElementBiz;
import com.github.xia.security.admin.biz.MenuBiz;
import com.github.xia.security.admin.biz.UserBiz;
import com.github.xia.security.admin.entity.BaseElement;
import com.github.xia.security.admin.entity.BaseMenu;
import com.github.xia.security.admin.entity.BaseUser;
import com.github.xia.security.common.constant.CommonConstant;
import com.github.xia.security.common.vo.PermissionInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author: xia
 * @date: 2020-03-14
 * @since: JDK 1.8
 * @version: 1.0
 */
@RequestMapping("/permissionFeignServer")
@Controller
public class PermissionFeignServer {

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private MenuBiz menuBiz;

    @Autowired
    private ElementBiz elementBiz;

    @RequestMapping(value = "/user/{username}/permissions", method = RequestMethod.GET,produces="application/json;charset=utf-8")
    @ResponseBody
    public List<PermissionInfo> getPermissionByUsername(@PathVariable("username") String username) {
        BaseUser baseUser = userBiz.getUserByUsername(username);
        List<BaseMenu> menus = menuBiz.getUserAuthorityMenuByUserId(baseUser.getId());
        List<PermissionInfo> result = new ArrayList<PermissionInfo>();
        PermissionInfo info = null;
        for(BaseMenu menu:menus){
            if(StringUtils.isBlank(menu.getHref())){
                continue;
            }
            info = new PermissionInfo();
            info.setCode(menu.getCode());
            info.setType(CommonConstant.RESOURCE_TYPE_MENU);
            info.setName(menu.getTitle());
            String uri = menu.getHref();
            if(!uri.startsWith("/")){
                uri = "/"+uri;
            }
            info.setUri(uri);
            info.setMethod(CommonConstant.RESOURCE_REQUEST_METHOD_GET);
            result.add(info
            );
            info.setMenu(menu.getTitle());
        }
        List<BaseElement> elements = elementBiz.getAuthorityElementByUserId(baseUser.getId()+"");
        for(BaseElement element:elements){
            info = new PermissionInfo();
            info.setCode(element.getCode());
            info.setType(element.getType());
            info.setUri(element.getUri());
            info.setMethod(element.getMethod());
            info.setName(element.getName());
            info.setMenu(element.getMenuId());
            result.add(info);
        }
        return result;
    }
}
