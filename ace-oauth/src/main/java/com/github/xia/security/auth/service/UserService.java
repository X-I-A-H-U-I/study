package com.github.xia.security.auth.service;

import com.github.xia.security.auth.domain.LoginUser;

/**
 * <p>
 * 用户service接口
 * </p>
 *
 * @author: xia
 * @date: 2020-03-30
 * @since: JDK 1.8
 * @version: 1.0
 */
public interface UserService {

    /**
     * 根据用户名称获取用户信息
     *
     * @param username
     * @return
     */
    LoginUser getUserByUsername(String username);
}
