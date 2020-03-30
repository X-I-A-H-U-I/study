package com.github.xia.security.admin.vo;

import com.github.xia.security.common.vo.TreeNode;

/**
 * @explain：
 * @author: XIA
 * @date: 2020-03-11
 * @since: JDK 1.8
 * @version: 1.0
 */
public class GroupTreeVO extends TreeNode {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
