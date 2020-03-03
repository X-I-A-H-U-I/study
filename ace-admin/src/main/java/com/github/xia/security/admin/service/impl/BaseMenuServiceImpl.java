package com.github.xia.security.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xia.security.admin.entity.BaseMenu;
import com.github.xia.security.admin.mapper.BaseMenuMapper;
import com.github.xia.security.admin.service.BaseMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BaseMenuServiceImpl extends ServiceImpl<BaseMenuMapper, BaseMenu> implements BaseMenuService {

    @Autowired
    private BaseMenuMapper baseMenuMapper;

    /**
     * 添加菜单
     *
     * @param baseMenu
     */
    @Override
    public void addMenu(BaseMenu baseMenu) {
        baseMenuMapper.insert(baseMenu);
    }

    /**
     * 查询总数
     *
     * @return
     */
    @Override
    public int selectCountByMenu(BaseMenu menu) {
        QueryWrapper<BaseMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title",menu.getTitle());
        return baseMenuMapper.selectCount(queryWrapper);
    }

    /**
     * 查询菜单集合
     *
     * @param baseMenu
     * @return
     */
    @Override
    public List<BaseMenu> findListByMenu(BaseMenu baseMenu) {
        QueryWrapper<BaseMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title",baseMenu.getTitle());
        return baseMenuMapper.selectList(queryWrapper);
    }
}
