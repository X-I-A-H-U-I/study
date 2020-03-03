package com.github.xia.security.admin.controller;


import com.github.pagehelper.PageHelper;
import com.github.xia.security.admin.entity.BaseMenu;
import com.github.xia.security.admin.service.BaseMenuService;
import com.github.xia.security.common.msg.ObjectRestResponse;
import com.github.xia.security.common.msg.TableResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xia
 * @since 2020-03-02
 */
@Controller
@RequestMapping("/menu")
public class BaseMenuController {

    @Autowired
    private BaseMenuService baseMenuService;

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<BaseMenu> page(int limit, int offset, String title){
        BaseMenu baseMenu = new BaseMenu();
        baseMenu.setTitle(title);
        int count = baseMenuService.selectCountByMenu(baseMenu);
        PageHelper.startPage(offset, limit);
        return new TableResultResponse<BaseMenu>(count,baseMenuService.findListByMenu(baseMenu));
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<BaseMenu> add(BaseMenu menu){
        baseMenuService.addMenu(menu);
        return new ObjectRestResponse<BaseMenu>().rel(true);
    }

}

