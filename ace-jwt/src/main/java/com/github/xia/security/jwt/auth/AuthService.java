package com.github.xia.security.jwt.auth;

import com.github.xia.security.jwt.vo.JwtUser;

/**
 * <p>
 * jwt操作token接口
 * </p>
 *
 * @author: xia
 * @date: 2020-03-22
 * @since: JDK 1.8
 * @version: 1.0
 */
public interface AuthService {

    /**
     * 登录操作
     *
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

    /**
     * 刷新操作
     *
     * @param oldToken
     * @return
     */
    String refresh(String oldToken);

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    JwtUser getJwtUserByToken(String token);
}
