package com.github.xia.security.api.feign;

import com.github.xia.security.common.vo.ClientInfo;
import com.github.xia.security.common.vo.LogInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>gate client feign客户端</p>
 *
 * @author: xia
 * @date: 2020-03-13
 * @since: JDK 1.8
 * @version: 1.0
 */
@FeignClient(value = "ace-admin/gateFeignServer")
public interface GateFeignClient {

    /**
     * 新增日志操作
     * @param info
     */
    @RequestMapping(value="/log/save",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    public void saveLog(LogInfo info);

    /**
     * 获取网关客户
     * @param clientId
     * @return
     */
    @RequestMapping(value = "/gate/client/{clientId}", method = RequestMethod.GET,produces="application/json;charset=utf-8")
    public ClientInfo getGateClientInfo(@PathVariable("clientId") String clientId);

}
