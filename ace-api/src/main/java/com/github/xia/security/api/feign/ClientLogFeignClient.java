package com.github.xia.security.api.feign;

import com.github.xia.security.common.vo.LogInfo;
import com.github.xia.security.common.vo.PermissionInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * <p>gate client feign客户端</p>
 *
 * @author: xia
 * @date: 2020-03-13
 * @since: JDK 1.8
 * @version: 1.0
 */
@FeignClient(value = "ace-admin/clientLogFeignServer")
public interface ClientLogFeignClient {

    /**
     * 新增日志操作
     * @param info
     */
    @RequestMapping(value="/log/save",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    public void saveLog(LogInfo info);

}
