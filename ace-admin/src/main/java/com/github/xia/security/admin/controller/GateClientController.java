package com.github.xia.security.admin.controller;

import com.github.pagehelper.PageHelper;
import com.github.xia.security.admin.biz.GateClientBiz;
import com.github.xia.security.admin.entity.GateClient;
import com.github.xia.security.common.msg.TableResultResponse;
import com.github.xia.security.common.rest.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

/**
 * <p>
 *     官网客户前端控制器
 * </p>
 *
 * @author: xia
 * @date: 2020-03-20
 * @since: JDK 1.8
 * @version: 1.0
 */
@Controller
@RequestMapping("gateClient")
public class GateClientController extends BaseController<GateClientBiz, GateClient> {

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<GateClient> page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1")int offset, String name){
        Example example = new Example(GateClient.class);
        if(StringUtils.isNotBlank(name)) {
            example.createCriteria().andLike("name", "%" + name + "%");
            example.or().andLike("code", "%" + name + "%");
        }
        int count = baseBiz.selectCountByExample(example);
        PageHelper.startPage(offset, limit);
        return new TableResultResponse<GateClient>(count,baseBiz.selectByExample(example));
    }

}
