package com.github.xia.security.admin.rpc;

import com.alibaba.fastjson.JSONObject;
import com.github.xia.security.admin.biz.ElementBiz;
import com.github.xia.security.admin.biz.MenuBiz;
import com.github.xia.security.admin.biz.UserBiz;
import com.github.xia.security.admin.entity.BaseElement;
import com.github.xia.security.admin.entity.BaseMenu;
import com.github.xia.security.admin.entity.BaseUser;
import com.github.xia.security.admin.vo.MenuTreeVO;
import com.github.xia.security.common.constant.CommonConstant;
import com.github.xia.security.common.util.TreeUtil;
import com.github.xia.security.common.vo.PermissionInfo;
import com.github.xia.security.common.vo.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> feign 服务</p>
 * @author: XIA
 * @date: 2020-03-13
 * @since: JDK 1.8
 * @version: 1.0
 */
@RequestMapping("/userFeignServer")
@Controller
public class UserFeignServer {

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private ElementBiz elementBiz;

    @Autowired
    private MenuBiz menuBiz;

    @RequestMapping(value = "/user/username/{username}",method = RequestMethod.GET, produces="application/json;charset=utf-8")
    @ResponseBody
    public UserInfo getUserByUsername(@PathVariable("username")String username) {
        UserInfo info = new UserInfo();
        BaseUser user = userBiz.getUserByUsername(username);
        BeanUtils.copyProperties(user,info);
        info.setId(user.getId().toString());
        return info;
    }

    @RequestMapping(value = "/user/{username}/system", method = RequestMethod.GET,produces="application/json;charset=utf-8")
    @ResponseBody
    public String getSystemsByUsername(@PathVariable("username") String username){
        int userId = userBiz.getUserByUsername(username).getId();
        return JSONObject.toJSONString(menuBiz.getUserAuthoritySystemByUserId(userId));
    }
    @RequestMapping(value = "/user/{username}/menu/parent/{parentId}", method = RequestMethod.GET,produces="application/json;charset=utf-8")
    @ResponseBody
    public String getMenusByUsername(@PathVariable("username") String username,@PathVariable("parentId")Integer parentId){
        int userId = userBiz.getUserByUsername(username).getId();
        try {
            if (parentId == null||parentId<0) {
                parentId = menuBiz.getUserAuthoritySystemByUserId(userId).get(0).getId();
            }
        } catch (Exception e) {
            return JSONObject.toJSONString(new ArrayList<MenuTreeVO>());
        }
        String jsonString = JSONObject.toJSONString(getMenuTree(menuBiz.getUserAuthorityMenuByUserId(userId), parentId));
        return jsonString;
    }

    private List<MenuTreeVO> getMenuTree(List<BaseMenu> menus,int root) {
        List<MenuTreeVO> trees = new ArrayList<MenuTreeVO>();
        MenuTreeVO node = null;
        for (BaseMenu menu : menus) {
            node = new MenuTreeVO();
            BeanUtils.copyProperties(menu, node);
            trees.add(node);
        }
        return TreeUtil.bulid(trees, root) ;
    }
}
