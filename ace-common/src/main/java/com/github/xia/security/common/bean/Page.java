package com.github.xia.security.common.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @explain：分页对象
 * @author: XIA
 * @date: 2020-02-02
 * @since: JDK 1.8
 * @version: 1.0
 */
@Getter
@Setter
@ToString
public class Page {
    int num;
    int size;
}
