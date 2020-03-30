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
@Setter
@Getter
@ToString
public class LogInfo implements Serializable {

    private String menu;

    private String opt;

    private String uri;


    private Date crtTime;

    private String crtUser;

    private String crtName;

    private String crtHost;

    public LogInfo(String menu, String option, String uri,  Date crtTime, String crtUser, String crtName, String crtHost) {
        this.menu = menu;
        this.opt = option;
        this.uri = uri;
        this.crtTime = crtTime;
        this.crtUser = crtUser;
        this.crtName = crtName;
        this.crtHost = crtHost;
    }
}
