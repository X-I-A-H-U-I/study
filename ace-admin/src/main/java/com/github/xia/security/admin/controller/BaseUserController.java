package com.github.xia.security.admin.controller;


import com.github.pagehelper.PageHelper;
import com.github.xia.security.admin.entity.BaseUser;
import com.github.xia.security.admin.service.BaseUserService;
import com.github.xia.security.common.msg.ListRestResponse;
import com.github.xia.security.common.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xia
 * @since 2020-03-02
 */
@Controller
@RequestMapping("/user")
public class BaseUserController {

    @Autowired
    private BaseUserService baseUserService;

    @RequestMapping(value = "/page/{pageIndex}/{pageSize}",method = RequestMethod.GET)
    @ResponseBody
    public List<BaseUser> list(@PathVariable int pageIndex, @PathVariable int pageSize){
        PageHelper.startPage(pageIndex, pageSize);
        return baseUserService.findUserList();
    }
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public ListRestResponse<List<BaseUser>> page(int pageIndex, int pageSize){
        int count = baseUserService.findCount();
        PageHelper.startPage(pageIndex, pageSize);
        return new ListRestResponse<List<BaseUser>>().rel(true).result(baseUserService.findUserList()).count(count);
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<BaseUser> add(BaseUser user){
        baseUserService.addUser(user);
        return new ObjectRestResponse<BaseUser>().rel(true);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<BaseUser> get(@PathVariable int id){
        return new ObjectRestResponse<BaseUser>().rel(true).result(baseUserService.findUserById(id));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<BaseUser> get(BaseUser user){
        baseUserService.updateUserById(user);
        return new ObjectRestResponse<BaseUser>().rel(true);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ObjectRestResponse<BaseUser> remove(@PathVariable int id){
        baseUserService.deleteUserById(id);
        return new ObjectRestResponse<BaseUser>().rel(true);
    }
}

