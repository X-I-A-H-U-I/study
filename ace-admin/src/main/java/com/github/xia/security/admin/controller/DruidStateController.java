package com.github.xia.security.admin.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @explain：druid监控
 * @author: XIA
 * @date: 2020-02-02
 * @since: JDK 1.8
 * @version: 1.0
 */
@RestController
public class DruidStateController {
    @GetMapping("/state")
    public Object druidStat() {
        // DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
}
