package com.github.xia.security.admin.biz;

import com.github.xia.security.admin.entity.BaseMenu;
import com.github.xia.security.admin.entity.BaseUser;
import com.github.xia.security.admin.mapper.BaseMenuMapper;
import com.github.xia.security.admin.mapper.BaseUserMapper;
import com.github.xia.security.common.biz.BaseBiz;
import com.github.xia.security.common.constant.UserConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-08 16:23
 */
@Service
public class UserBiz extends BaseBiz<BaseUserMapper, BaseUser> {

    @Autowired
    private BaseMenuMapper menuMapper;

    @Override
    public void insertSelective(BaseUser entity) {
        String password = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT).encode(entity.getPassword());
        entity.setPassword(password);
        super.insertSelective(entity);
    }

    @Override
    public void updateById(BaseUser entity) {
        String password = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT).encode(entity.getPassword());
        entity.setPassword(password);
        super.updateById(entity);
    }

    public BaseUser getUserByUsername(String username){
        BaseUser user = new BaseUser();
        user.setUsername(username);
        return selectOne(user);
    }

}
