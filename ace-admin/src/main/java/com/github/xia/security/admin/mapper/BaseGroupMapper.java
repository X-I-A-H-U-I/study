package com.github.xia.security.admin.mapper;

import com.github.xia.security.admin.entity.BaseGroup;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xia
 * @since 2020-03-02
 */
public interface BaseGroupMapper extends  Mapper<BaseGroup> {

    void deleteGroupMembersById (@Param("groupId") int groupId);

    void deleteGroupLeadersById (@Param("groupId") int groupId);

    void insertGroupMembersById (@Param("groupId") int groupId,@Param("userId") int userId);

    void insertGroupLeadersById (@Param("groupId") int groupId,@Param("userId") int userId);
}
