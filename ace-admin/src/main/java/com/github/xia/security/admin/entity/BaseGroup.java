package com.github.xia.security.admin.entity;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Date;

import com.github.xia.security.common.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>
 * 
 * </p>
 *
 * @author xia
 * @since 2020-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="BaseGroup对象", description="")
@Table(name = "base_group")
public class BaseGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    private String code;

    private String name;

    @Column(name = "parent_id")
    private Integer parentId;

    private String path;

    private String type;

    @Column(name = "group_type")
    private Integer groupType = CommonConstant.DEFAULT_GROUP_TYPE;

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
