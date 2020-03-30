package com.github.xia.security.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author xiahui
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.github.xia.security.admin.mapper","com.github.xia.security.common"})
@ServletComponentScan("com.github.xia.security.admin.config")
public class AceAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AceAdminApplication.class, args);
    }

}
