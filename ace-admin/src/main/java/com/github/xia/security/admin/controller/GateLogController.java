package com.github.xia.security.admin.controller;

import com.github.pagehelper.PageHelper;
import com.github.xia.security.admin.biz.GateLogBiz;
import com.github.xia.security.admin.entity.GateLog;
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
 *     网关客户操作日志
 * </p>
 *
 * @author: xia
 * @date: 2020-03-22
 * @since: JDK 1.8
 * @version: 1.0
 */
@Controller
@RequestMapping("gateLog")
public class GateLogController extends BaseController<GateLogBiz, GateLog> {

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<GateLog> page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1")int offset, String name){
        Example example = new Example(GateLog.class);
        if(StringUtils.isNotBlank(name)) {
            example.createCriteria().andLike("menu", "%" + name + "%");
        }
        int count = baseBiz.selectCountByExample(example);
        PageHelper.startPage(offset, limit);
        return new TableResultResponse<GateLog>(count,baseBiz.selectByExample(example));
    }

}
