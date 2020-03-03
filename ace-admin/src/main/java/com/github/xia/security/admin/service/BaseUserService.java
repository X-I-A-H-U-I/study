package com.github.xia.security.admin.service;

import com.github.xia.security.admin.entity.BaseUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xia
 * @since 2020-03-02
 */
public interface BaseUserService extends IService<BaseUser> {

    /**
     * 更新用户
     * @param baseUser
     */
    void updateUserById(BaseUser baseUser);

    /**
     * 新增用户
     * @param baseUser
     */
    void addUser(BaseUser baseUser);

    /**
     * 查询用户列表集合
     * @return
     */
    List<BaseUser> findUserList();

    /**
     * 查询总数
     * @return
     */
    int findCount();

    /**
     *根据用户id查询用户
     * @param id
     * @return
     */
    BaseUser findUserById(int id);

    /**
     * 删除用户通过id
     * @param id
     */
    void deleteUserById(int id);
}
