package com.github.xia.security.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>监控登录页面</p>
 * @author: XIA
 * @date: 2020-03-12
 * @since: JDK 1.8
 * @version: 1.0
 */
@Controller
public class SecurityController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login() {
        return "login";
    }
}
