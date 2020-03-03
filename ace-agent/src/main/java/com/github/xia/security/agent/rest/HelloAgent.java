package com.github.xia.security.agent.rest;

import com.github.xia.security.api.IHello;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @explain：
 * @author: XIA
 * @date: 2020-02-01
 * @since: JDK 1.8
 * @version: 1.0
 */
@RestController
@RequestMapping("/agent")
public class HelloAgent {

    @Autowired
    private IHello iHello;

    @HystrixCommand(fallbackMethod = "helloFallbackMethod")
    @GetMapping("/say")
    public String sayHello(){
        return iHello.getHello();
    }

    public String helloFallbackMethod() {
        return "熔断器调用，回调失败！";
    }
}
