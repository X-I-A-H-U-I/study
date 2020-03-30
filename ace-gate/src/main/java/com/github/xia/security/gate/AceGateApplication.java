package com.github.xia.security.gate;

import com.github.xia.security.gate.thread.LogThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 路由网关
 * @author xiahui
 */
@EnableZuulProxy
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.github.xia.security.api.feign")
@SpringBootApplication
public class AceGateApplication {

    public static void main(String[] args) {
        LogThread.getInstance().start();
        SpringApplication.run(AceGateApplication.class, args);
    }

}



