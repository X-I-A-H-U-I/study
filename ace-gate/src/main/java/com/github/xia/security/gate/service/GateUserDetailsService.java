package com.github.xia.security.gate.service;

import com.github.xia.security.api.feign.UserFeignClient;
import com.github.xia.security.common.vo.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * <p></p>
 *
 * @author: xia
 * @date: 2020-03-13
 * @since: JDK 1.8
 * @version: 1.0
 */
@Service
public class GateUserDetailsService implements UserDetailsService {

    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("用户名为空");
        }

        UserInfo info = userFeignClient.getUserByUsername(username);
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        return new org.springframework.security.core.userdetails.User(
                username, info.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
}
