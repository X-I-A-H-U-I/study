package com.github.xia.security.api.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Enumeration;

/**
 * <p>
 * 解决查询用户信息使用了feign,会有一些坑,比如token没有传递下来,token失效等等
 * </p>
 *
 * @author: xia
 * @date: 2020-03-19
 * @since: JDK 1.8
 * @version: 1.0
 */
@Configuration
public class FeignConfig implements RequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RequestInterceptor.class);

    private final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Called for every request. Add data using methods on the supplied {@link RequestTemplate}.
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        if (request != null) {
            log.error("调用feign传递header携带token");

//        只携带token
//            String authorization = request.getHeader(AUTHORIZATION_HEADER);
//            requestTemplate.header("Authorization", authorization);
//             System.err.println("Authorization :\t\t"+ authorization);

//        携带全部
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String values = request.getHeader(name);
                    template.header(name, values);
                    log.debug("name ：\t\t" + name);
                    log.debug("values ： \t\t" + values);

                }
            }
        }
    }
}
