package com.github.xia.security.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * gate -jwt 鉴权
 * @author xiahui
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.github.xia.security.api.feign")
//@EnableZuulProxy
public class AceJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(AceJwtApplication.class, args);
    }
}
