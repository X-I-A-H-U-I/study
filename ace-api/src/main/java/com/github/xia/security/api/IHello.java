package com.github.xia.security.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @explain：
 * @author: XIA
 * @date: 2020-01-31
 * @since: JDK 1.8
 * @version: 1.0
 */
@FeignClient("ace-admin")
public interface IHello {

    /**
     * 测试方法1
     * @return
     */
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    String getHello();
}
