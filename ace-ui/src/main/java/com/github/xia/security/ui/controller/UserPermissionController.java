package com.github.xia.security.ui.controller;

import com.github.xia.security.api.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p></p>
 *
 * @author: xia
 * @date: 2020-03-18
 * @since: JDK 1.8
 * @version: 1.0
 */
@Controller
public class UserPermissionController {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/user/system",method = RequestMethod.GET,produces="application/json;charset=utf-8")
    @ResponseBody
    public String getUserSystem(){
        return userFeignClient.getSystemsByUsername(getCurrentUserName());
    }
    
    @RequestMapping(value = "/user/menu",method = RequestMethod.GET,produces="application/json;charset=utf-8")
    @ResponseBody
    public String getUserMenu(@RequestParam(defaultValue = "-1") Integer parentId){
        String userMenu = userFeignClient.getMenusByUsername(getCurrentUserName(), parentId);
        return userMenu;
    }

    public String getCurrentUserName(){
        String authorization = request.getHeader("Authorization");
        return new String(Base64Utils.decodeFromString(authorization));
    }
}
