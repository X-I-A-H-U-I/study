package com.github.xia.security.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @author xiahui
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@MapperScan(basePackages = {"com.github.xia.security.admin.mapper"})
public class AceAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AceAdminApplication.class, args);
    }

}
