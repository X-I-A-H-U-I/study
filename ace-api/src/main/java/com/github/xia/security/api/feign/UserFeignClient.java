package com.github.xia.security.api.feign;

import com.github.xia.security.common.vo.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>user feign客户端</p>
 *
 * @author: xia
 * @date: 2020-03-13
 * @since: JDK 1.8
 * @version: 1.0
 */
@FeignClient(value = "ace-admin/userFeignServer")
public interface UserFeignClient {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @RequestMapping(value = "/user/username/{username}",method = RequestMethod.GET, produces="application/json;charset=utf-8")
    @ResponseBody
    UserInfo getUserByUsername(@PathVariable("username")String username);

    @RequestMapping(value = "/user/{username}/system", method = RequestMethod.GET,produces="application/json;charset=utf-8")
    @ResponseBody
    String getSystemsByUsername(@PathVariable("username") String username);

    @RequestMapping(value = "/user/{username}/menu/parent/{parentId}", method = RequestMethod.GET,produces="application/json;charset=utf-8")
    @ResponseBody
    String getMenusByUsername(@PathVariable("username") String username,@PathVariable("parentId")Integer parentId);

}
