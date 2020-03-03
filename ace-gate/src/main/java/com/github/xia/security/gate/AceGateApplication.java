package com.github.xia.security.gate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 路由网关
 * @author xiahui
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class AceGateApplication {

    public static void main(String[] args) {
        SpringApplication.run(AceGateApplication.class, args);
    }

}
