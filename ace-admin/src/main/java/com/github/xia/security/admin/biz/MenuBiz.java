package com.github.xia.security.admin.biz;

import com.github.xia.security.admin.entity.BaseMenu;
import com.github.xia.security.admin.mapper.BaseMenuMapper;
import com.github.xia.security.common.biz.BaseBiz;
import com.github.xia.security.common.constant.CommonConstant;
import com.github.xia.security.common.util.TreeUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-12 8:48
 */
@Service
public class MenuBiz extends BaseBiz<BaseMenuMapper, BaseMenu> {

    @Override
    public void insertSelective(BaseMenu entity) {
        if(CommonConstant.ROOT == entity.getParentId()){
            entity.setPath("/"+entity.getCode());
        }else{
            BaseMenu parent = this.selectById(entity.getParentId());
            entity.setPath(parent.getPath()+"/"+entity.getCode());
        }
        super.insertSelective(entity);
    }

    @Override
    public void updateById(BaseMenu entity) {
        if(CommonConstant.ROOT == entity.getParentId()){
            entity.setPath("/"+entity.getCode());
        }else{
            BaseMenu parent = this.selectById(entity.getParentId());
            entity.setPath(parent.getPath()+"/"+entity.getCode());
        }
        super.updateById(entity);
    }

    /**
     * 获取用户可以访问的菜单
     * @param id
     * @return
     */
    public List<BaseMenu> getUserAuthorityMenuByUserId(int id){
        return mapper.selectAuthorityMenuByUserId(id);
    }

    /**
     * 根据用户获取可以访问的系统
     * @param id
     * @return
     */
    public List<BaseMenu> getUserAuthoritySystemByUserId(int id){
        List<BaseMenu> menus = mapper.selectAuthoritySystemByUserId(id);
        return menus;
    }
}
