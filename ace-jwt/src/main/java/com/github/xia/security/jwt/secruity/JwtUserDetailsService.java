package com.github.xia.security.jwt.secruity;

import com.github.xia.security.api.feign.GateFeignClient;
import com.github.xia.security.api.feign.UserFeignClient;
import com.github.xia.security.common.vo.ClientInfo;
import com.github.xia.security.common.vo.UserInfo;
import com.github.xia.security.jwt.vo.JwtUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * JwtUserDetailsService
 * 实现UserDetailsService,重写loadUserByUsername方法
 * 返回随机生成的user,pass是密码,
 * 如果你自己需要定制查询user的方法,请改造这里
 * </p>
 *
 * @author: xia
 * @date: 2020-03-23
 * @since: JDK 1.8
 * @version: 1.0
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private GateFeignClient gateFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userFeignClient.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.createJwtUser(user);
        }
//        ClientInfo info = gateFeignClient.getGateClientInfo(clientId);
//        if (info == null) {
//            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", clientId));
//        } else {
//            return JwtUserFactory.create(info);
//        }
        //模拟测试
//        String pass = new BCryptPasswordEncoder().encode("pass");
//        if (StringUtils.isNotEmpty(clientId)&&clientId.contains("user")) {
//            return new JwtUser("1", clientId,pass,null, new Date(),false);
//        } else {
//            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", clientId));
//        }
    }
}
