package com.github.xia.security.api.feign;

import com.github.xia.security.common.vo.PermissionInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * <p>user feign客户端</p>
 *
 * @author: xia
 * @date: 2020-03-13
 * @since: JDK 1.8
 * @version: 1.0
 */
@FeignClient(value = "ace-admin/permissionFeignServer")
public interface PermissionFeignClient {

    /**
     * 根据用户查询资源
     * @param username
     * @return
     */
    @RequestMapping(value = "/user/{username}/permissions", method = RequestMethod.GET,produces="application/json;charset=utf-8")
    List<PermissionInfo> getPermissionByUsername(@PathVariable("username") String username);

}
