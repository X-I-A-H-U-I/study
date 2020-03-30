package com.github.xia.security.common.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p></p>
 *
 * @author: xia
 * @date: 2020-03-22
 * @since: JDK 1.8
 * @version: 1.0
 */
@ToString
@Getter
@Setter
public class ClientInfo implements Serializable {

    private Integer id;

    private String code;

    private String secret;

    private String name;

    private boolean isLocked;

    private String description;

    private Date crtTime;

    private String crtUser;

    private String crtName;

    private String crtHost;

    private Date updTime;

    private String updUser;

    private String updName;

    private String updHost;
}
