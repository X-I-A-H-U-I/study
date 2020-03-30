package com.github.xia.security.monitor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @explain：
 * @author: XIA
 * @date: 2020-02-24
 * @since: JDK 1.8
 * @version: 1.0
 */
//@Configuration
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").permitAll().and()
                .logout().logoutUrl("/logout").and().authorizeRequests()
                .antMatchers("/**/*.css", "/img/**", "/api/**")
                .permitAll().and().authorizeRequests().antMatchers("/**").authenticated();
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.httpBasic();
    }

}
