package com.github.xia.security.admin.service;

import com.github.xia.security.admin.entity.BaseMenu;
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
public interface BaseMenuService extends IService<BaseMenu> {

    /**
     * 添加菜单
     * @param baseMenu
     */
    void addMenu(BaseMenu baseMenu);

    /**
     * 查询总数
     * @param menu
     * @return
     */
    int selectCountByMenu(BaseMenu menu);

    /**
     * 查询菜单集合
     * @param baseMenu
     * @return
     */
    List<BaseMenu> findListByMenu(BaseMenu baseMenu);
}
