package com.github.xia.security.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author xiahui
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.github.xia.security.api.feign")
public class AceUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AceUiApplication.class, args);
	}

}

