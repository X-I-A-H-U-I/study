package com.github.xia.security.admin.controller;


import com.github.pagehelper.PageHelper;
import com.github.xia.security.admin.biz.UserBiz;
import com.github.xia.security.admin.entity.BaseUser;
import com.github.xia.security.common.msg.TableResultResponse;
import com.github.xia.security.common.rest.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xia
 * @since 2020-03-02
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController<UserBiz, BaseUser> {
    @RequestMapping(value = "/page/{pageIndex}/{pageSize}", method = RequestMethod.GET)
    @ResponseBody
    public List<BaseUser> list(@PathVariable int pageIndex, @PathVariable int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return baseBiz.selectListAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<BaseUser> page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int offset, String name) {
        Example example = new Example(BaseUser.class);
        if (StringUtils.isNotBlank(name)) {
            example.createCriteria().andLike("name", "%" + name + "%");
        }
        int count = baseBiz.selectCountByExample(example);
        PageHelper.startPage(offset, limit);
        return new TableResultResponse<BaseUser>(count, baseBiz.selectByExample(example));
    }
}

