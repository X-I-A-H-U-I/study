package com.github.xia.security.admin.mapper;

import com.github.xia.security.admin.entity.BaseResourceAuthority;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xia
 * @since 2020-03-12
 */
public interface BaseResourceAuthorityMapper extends  Mapper<BaseResourceAuthority> {

    void deleteByAuthorityIdAndResourceType(@Param("authorityId")String authorityId,@Param("resourceType") String resourceType);

}
