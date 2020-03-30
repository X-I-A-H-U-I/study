package com.github.xia.security.admin.mapper;

import com.github.xia.security.admin.entity.BaseElement;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p></p>
 *
 * @author: xia
 * @date: 2020-03-18
 * @since: JDK 1.8
 * @version: 1.0
 */
public interface BaseElementMapper extends Mapper<BaseElement> {

    /**
     *
     * @param userId
     * @return
     */
    List<BaseElement> selectAuthorityElementByUserId(@Param("userId")String userId);

    /**
     *
     * @param userId
     * @param menuId
     * @return
     */
    List<BaseElement> selectAuthorityMenuElementByUserId(@Param("userId")String userId, @Param("menuId")String menuId);
}
