package com.github.xia.security.admin.mapper;

import com.github.xia.security.admin.entity.BaseMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xia
 * @since 2020-03-02
 */
public interface BaseMenuMapper extends Mapper<BaseMenu> {

    /**
     *
     * @param authorityId
     * @param authorityType
     * @return
     */
    List<BaseMenu> selectMenuByAuthorityId(@Param("authorityId") String authorityId,@Param("authorityType") String authorityType);

    /**
     * 根据用户和组的权限关系查找用户可访问菜单
     * @param userId
     * @return
     */
    List<BaseMenu> selectAuthorityMenuByUserId (@Param("userId") int userId);

    /**
     * 根据用户和组的权限关系查找用户可访问的系统
     * @param userId
     * @return
     */
    List<BaseMenu> selectAuthoritySystemByUserId (@Param("userId") int userId);

}
