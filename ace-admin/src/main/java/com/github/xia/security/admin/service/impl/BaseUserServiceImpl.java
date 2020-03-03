package com.github.xia.security.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xia.security.admin.entity.BaseUser;
import com.github.xia.security.admin.mapper.BaseUserMapper;
import com.github.xia.security.admin.service.BaseUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.xia.security.common.constant.UserConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xia
 * @since 2020-03-02
 */
@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser> implements BaseUserService {

    @Autowired
    private BaseUserMapper baseUserMapper;

    /**
     * 更新用户
     *
     * @param baseUser
     */
    @Override
    public void updateUserById(BaseUser baseUser) {
        String password = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT).encode(baseUser.getPassword());
        baseUser.setPassword(password);
        baseUserMapper.updateById(baseUser);
    }

    /**
     * 新增用户
     *
     * @param baseUser
     */
    @Override
    public void addUser(BaseUser baseUser) {
        String password = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT).encode(baseUser.getPassword());
        baseUser.setPassword(password);
        baseUserMapper.insert(baseUser);
    }

    /**
     * 查询用户列表集合
     *
     * @return
     */
    @Override
    public List<BaseUser> findUserList() {
        QueryWrapper<BaseUser> userWrapper = new QueryWrapper();
        return baseUserMapper.selectList(userWrapper);
    }

    @Override
    public int findCount() {
        QueryWrapper<BaseUser> userWrapper = new QueryWrapper();
        return baseUserMapper.selectCount(userWrapper);
    }

    /**
     * 根据用户id查询用户
     *
     * @param id
     * @return
     */
    @Override
    public BaseUser findUserById(int id) {
        return baseUserMapper.selectById(id);
    }

    /**
     * 删除用户通过id
     *
     * @param id
     */
    @Override
    public void deleteUserById(int id) {
        baseUserMapper.deleteById(id);
    }
}
