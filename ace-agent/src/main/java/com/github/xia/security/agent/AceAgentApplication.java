package com.github.xia.security.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 消费方1
 * @author xiahui
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.github.xia"})
@EnableZuulProxy
@EnableFeignClients(basePackages = {"com.github.xia.security.api"})
public class AceAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AceAgentApplication.class, args);
	}

}
