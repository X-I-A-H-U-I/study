package com.github.xia.security.admin.mapper;

import com.github.xia.security.admin.entity.BaseUser;
import org.apache.ibatis.annotations.Param;
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
public interface BaseUserMapper extends  Mapper<BaseUser> {

    List<BaseUser> selectMemberByGroupId(@Param("groupId") int groupId);

    List<BaseUser> selectLeaderByGroupId(@Param("groupId") int groupId);
}
