package com.github.xia.security.admin.rest;

import com.github.xia.security.api.IHello;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @explain：
 * @author: XIA
 * @date: 2020-01-31
 * @since: JDK 1.8
 * @version: 1.0
 */

@RestController
public class HelloController{
    /**
     * 测试方法1
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "helloFallbackMethod")
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String getHello() {
        return "hello world !";
    }

    public String helloFallbackMethod(){
        return "failure";
    }
}
