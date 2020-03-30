package com.github.xia.security.admin.controller;


import com.github.xia.security.admin.biz.MenuBiz;
import com.github.xia.security.admin.biz.UserBiz;
import com.github.xia.security.admin.entity.BaseMenu;
import com.github.xia.security.admin.vo.AuthorityMenuTree;
import com.github.xia.security.admin.vo.MenuTreeVO;
import com.github.xia.security.common.constant.CommonConstant;
import com.github.xia.security.common.rest.BaseController;
import com.github.xia.security.common.util.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
@RequestMapping("/menu")
public class MenuController extends BaseController<MenuBiz,BaseMenu> {

    @Autowired
    private UserBiz userBiz;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<BaseMenu> list(String title) {
        Example example = new Example(BaseMenu.class);
        if (StringUtils.isNotBlank(title)) {
            example.createCriteria().andLike("title", "%" + title + "%");
        }
        return baseBiz.selectByExample(example);
    }

    @RequestMapping(value = "/system", method = RequestMethod.GET)
    @ResponseBody
    public List<BaseMenu> getSystem() {
        BaseMenu menu = new BaseMenu();
        menu.setParentId(CommonConstant.ROOT);
        return baseBiz.selectList(menu);
    }

    @RequestMapping(value = "/menuTree", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuTreeVO> listMenu(Integer parentId) {
        try {
            if (parentId == null) {
                parentId = this.getSystem().get(0).getId();
            }
        } catch (Exception e) {
            return new ArrayList<MenuTreeVO>();
        }
        List<MenuTreeVO> trees = new ArrayList<MenuTreeVO>();
        MenuTreeVO node = null;
        Example example = new Example(BaseMenu.class);
        BaseMenu parent = baseBiz.selectById(parentId);
        if(parent != null){
            example.createCriteria().andLike("path", parent.getPath() + "%").andNotEqualTo("id",parent.getId());
        }
        for (BaseMenu menu : baseBiz.selectByExample(example)) {
            node = new MenuTreeVO();
            BeanUtils.copyProperties(menu, node);
            trees.add(node);
        }
        return getMenuTree(baseBiz.selectByExample(example), parent.getId());
    }

    @RequestMapping(value = "/authorityTree", method = RequestMethod.GET)
    @ResponseBody
    public List<AuthorityMenuTree> listAuthorityMenu() {
        List<AuthorityMenuTree> trees = new ArrayList<AuthorityMenuTree>();
        AuthorityMenuTree node = null;
        for (BaseMenu menu : baseBiz.selectListAll()) {
            node = new AuthorityMenuTree();
            node.setText(menu.getTitle());
            BeanUtils.copyProperties(menu, node);
            trees.add(node);
        }
        return TreeUtil.bulid(trees,CommonConstant.ROOT);
    }

    @RequestMapping(value = "/user/authorityTree", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuTreeVO> listUserAuthorityMenu(Integer parentId){
        int userId = userBiz.getUserByUsername(getCurrentUserName()).getId();
        try {
            if (parentId == null) {
                parentId = this.getSystem().get(0).getId();
            }
        } catch (Exception e) {
            return new ArrayList<MenuTreeVO>();
        }
        return getMenuTree(baseBiz.getUserAuthorityMenuByUserId(userId),parentId);
    }

    @RequestMapping(value = "/user/system", method = RequestMethod.GET)
    @ResponseBody
    public List<BaseMenu> listUserAuthoritySystem() {
        int userId = userBiz.getUserByUsername(getCurrentUserName()).getId();
        return baseBiz.getUserAuthoritySystemByUserId(userId);
    }

    private List<MenuTreeVO> getMenuTree(List<BaseMenu> menus,int root) {
        List<MenuTreeVO> trees = new ArrayList<MenuTreeVO>();
        MenuTreeVO node = null;
        for (BaseMenu menu : menus) {
            node = new MenuTreeVO();
            BeanUtils.copyProperties(menu, node);
            trees.add(node);
        }
        return TreeUtil.bulid(trees,root) ;
    }
}

