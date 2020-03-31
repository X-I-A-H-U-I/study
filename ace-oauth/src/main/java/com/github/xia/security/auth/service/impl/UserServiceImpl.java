package com.github.xia.security.auth.service.impl;

import com.github.xia.security.auth.dao.UserMapper;
import com.github.xia.security.auth.domain.LoginUser;
import com.github.xia.security.auth.service.UserService;
import com.github.xia.security.common.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户service实现
 * </p>
 *
 * @author: xia
 * @date: 2020-03-30
 * @since: JDK 1.8
 * @version: 1.0
 */
@Service
public class UserServiceImpl extends BaseBiz<UserMapper, LoginUser> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名称获取用户信息
     *
     * @param username
     * @return
     */
    @Override
    public LoginUser getUserByUsername(String username) {
        LoginUser loginUser = userMapper.getUserByUsername(username);
        return loginUser;
    }
}
