package com.github.xia.security.admin.biz;

import com.github.xia.security.admin.entity.BaseElement;
import com.github.xia.security.admin.mapper.BaseElementMapper;
import com.github.xia.security.common.biz.BaseBiz;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p></p>
 *
 * @author: xia
 * @date: 2020-03-18
 * @since: JDK 1.8
 * @version: 1.0
 */
@Service
public class ElementBiz extends BaseBiz<BaseElementMapper, BaseElement> {

    public List<BaseElement> getAuthorityElementByUserId(String userId){
        return mapper.selectAuthorityElementByUserId(userId);
    }

    public List<BaseElement> getAuthorityElementByUserId(String userId,String menuId){
        return mapper.selectAuthorityMenuElementByUserId(userId,menuId);
    }
}
