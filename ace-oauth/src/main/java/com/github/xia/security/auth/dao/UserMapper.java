package com.github.xia.security.auth.dao;

import com.github.xia.security.auth.domain.LoginUser;
import tk.mybatis.mapper.common.Mapper;

/**
 * <p></p>
 *
 * @author: xia
 * @date: 2020-03-30
 * @since: JDK 1.8
 * @version: 1.0
 */
public interface UserMapper extends Mapper<LoginUser> {

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    LoginUser getUserByUsername(String username);

}
