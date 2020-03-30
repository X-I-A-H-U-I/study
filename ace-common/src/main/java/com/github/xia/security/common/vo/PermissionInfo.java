package com.github.xia.security.common.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * <p></p>
 *
 * @author: xia
 * @date: 2020-03-13
 * @since: JDK 1.8
 * @version: 1.0
 */
@Getter
@Setter
public class PermissionInfo {
    private String code;
    private String type;
    private String uri;
    private String method;
    private String name;
    private String menu;

}
