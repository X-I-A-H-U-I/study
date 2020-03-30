package com.github.xia.security.admin.controller;

import com.github.xia.security.admin.biz.ElementBiz;
import com.github.xia.security.admin.biz.UserBiz;
import com.github.xia.security.admin.entity.BaseElement;
import com.github.xia.security.common.msg.ObjectRestResponse;
import com.github.xia.security.common.msg.TableResultResponse;
import com.github.xia.security.common.rest.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p></p>
 *
 * @author: xia
 * @date: 2020-03-18
 * @since: JDK 1.8
 * @version: 1.0
 */
@Controller
@RequestMapping("/element")
public class ElementController extends BaseController<ElementBiz, BaseElement> {

    @Autowired
    private UserBiz userBiz;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<BaseElement> page(@RequestParam(defaultValue = "10") int limit,
                                                 @RequestParam(defaultValue = "1") int offset, @RequestParam(defaultValue = "0") int menuId) {
        BaseElement element = new BaseElement();
        element.setMenuId(menuId + "");
        List<BaseElement> elements = baseBiz.selectList(element);
        return new TableResultResponse<BaseElement>(elements.size(), elements);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<BaseElement> getAuthorityElement(String menuId) {
        int userId = userBiz.getUserByUsername(getCurrentUserName()).getId();
        List<BaseElement> elements = baseBiz.getAuthorityElementByUserId(userId + "", menuId);
        return new ObjectRestResponse<List<BaseElement>>().rel(true).result(elements);
    }

    @RequestMapping(value = "/user/menu", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<BaseElement> getAuthorityElement() {
        int userId = userBiz.getUserByUsername(getCurrentUserName()).getId();
        List<BaseElement> elements = baseBiz.getAuthorityElementByUserId(userId + "");
        return new ObjectRestResponse<List<BaseElement>>().rel(true).result(elements);
    }

}

