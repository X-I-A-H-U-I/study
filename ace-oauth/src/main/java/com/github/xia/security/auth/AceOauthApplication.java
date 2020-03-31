package com.github.xia.security.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * oauth2+jwt+oauth2+redis认证服务器
 * @author xiahui
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.github.xia.security.api.feign")
@MapperScan(basePackages = {"com.github.xia.security.auth.dao"})
public class AceOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AceOauthApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
