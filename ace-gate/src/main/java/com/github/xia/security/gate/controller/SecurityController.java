package com.github.xia.security.gate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @explain：登录验证控制
 * @author: XIA
 * @date: 2020-02-24
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
