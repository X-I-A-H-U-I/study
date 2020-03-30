package com.github.xia.security.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * <p>
 *     认证前端控制器
 * </p>
 *
 * @author: xia
 * @date: 2020-03-20
 * @since: JDK 1.8
 * @version: 1.0
 */
@RestController
@Api(tags="认证用户信息")
@RequestMapping("/user")
@Slf4j
public class AuthController {

    @Autowired
    private ConsumerTokenServices tokenServices;

    /**
     * 获取授权用户的信息
     * @param user 当前用户
     * @return 授权信息
     */
    @ApiOperation(value = "获取Principal信息", notes = "获取当前用户的认证对象")
    @GetMapping("/userInfo")
    public Principal user(Principal user){
        log.info("反向注入查询登录用户: "+user);
        return user;
    }

    /**
     * 用户登出
     * @param access_token 当前用户登录token信息
     * @return 处理成功信息
     */
    @ApiOperation(value = "用户登出", notes = "用户登出，移除当前token")
    @GetMapping("/logout")
    public ResponseEntity<String> logoutUser(String access_token){
        tokenServices.revokeToken(access_token);
        log.info("登录TOKEN信息: "+access_token);
        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

}
