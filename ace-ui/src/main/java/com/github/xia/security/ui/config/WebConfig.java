package com.github.xia.security.ui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @explain：springBoot+thymeleaf访问静态资源
 * @author: XIA
 * @date: 2020-02-02
 * @since: JDK 1.8
 * @version: 1.0
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    /**
      * SpringBoot 2.x要重写该方法，不然css、js、image 等静态资源路径无法访问
      * @param registry
      */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        super.addResourceHandlers(registry);
    }

}
