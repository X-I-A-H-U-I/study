package com.github.xia.security.common.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
public class UserInfo implements Serializable {

    public String id;

    public String username;

    public String password;

    public String name;

    private String description;

}
