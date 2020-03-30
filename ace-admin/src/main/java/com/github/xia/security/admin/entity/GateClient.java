package com.github.xia.security.admin.entity;

import com.github.xia.security.common.constant.CommonConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>
 *     网关客户
 * </p>
 *
 * @author: xia
 * @date: 2020-03-20
 * @since: JDK 1.8
 * @version: 1.0
 */
@Getter
@Setter
@ToString
@Table(name = "gate_client")
public class GateClient {
    @Id
    private Integer id;

    private String code;

    private String secret;

    private String name;

    private String locked = CommonConstant.BOOLEAN_NUMBER_FALSE;

    private String description;

    @Column(name = "crt_time")
    private Date crtTime;

    @Column(name = "crt_user")
    private String crtUser;

    @Column(name = "crt_name")
    private String crtName;

    @Column(name = "crt_host")
    private String crtHost;

    @Column(name = "upd_time")
    private Date updTime;

    @Column(name = "upd_user")
    private String updUser;

    @Column(name = "upd_name")
    private String updName;

    @Column(name = "upd_host")
    private String updHost;

    private String attr1;

    private String attr2;

    private String attr3;

    private String attr4;

    private String attr5;

    private String attr6;

    private String attr7;

    private String attr8;
}
