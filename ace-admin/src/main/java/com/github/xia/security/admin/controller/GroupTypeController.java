package com.github.xia.security.admin.controller;


import com.github.pagehelper.PageHelper;
import com.github.xia.security.admin.biz.GroupTypeBiz;
import com.github.xia.security.admin.entity.BaseGroupType;
import com.github.xia.security.admin.entity.BaseUser;
import com.github.xia.security.common.msg.TableResultResponse;
import com.github.xia.security.common.rest.BaseController;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/groupType")
public class GroupTypeController extends BaseController<GroupTypeBiz, BaseGroupType> {

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<BaseGroupType> page(int limit, int offset, String name){
        Example example = new Example(BaseUser.class);
        if(StringUtils.isNotBlank(name)) {
            example.createCriteria().andLike("name", "%" + name + "%");
        }
        int count = baseBiz.selectCountByExample(example);
        PageHelper.startPage(offset, limit);
        return new TableResultResponse<BaseGroupType>(count,baseBiz.selectByExample(example));
    }


}

