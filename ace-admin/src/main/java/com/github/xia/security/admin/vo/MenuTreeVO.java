package com.github.xia.security.admin.vo;

import com.github.xia.security.common.vo.TreeNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @explain：
 * @author: XIA
 * @date: 2020-03-10
 * @since: JDK 1.8
 * @version: 1.0
 */
@Getter
@Setter
@ToString
public class MenuTreeVO extends TreeNode {
    private String icon;
    private String title;
    private String href;
    private boolean spread = false;
}
