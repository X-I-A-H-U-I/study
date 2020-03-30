package com.github.xia.security.auth.service;

import com.github.xia.security.api.feign.UserFeignClient;
import com.github.xia.security.auth.domain.OauthUserFactory;
import com.github.xia.security.common.util.DfUtils;
import com.github.xia.security.common.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * oauth业务授权实现UserDetailsService,重写loadUserByUsername方法
 * </p>
 *
 * @author: xia
 * @date: 2020-03-26
 * @since: JDK 1.8
 * @version: 1.0
 */
@Service
public class AuthUserDetailService implements UserDetailsService {

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
        UserInfo user = userFeignClient.getUserByUsername(username);
        if (DfUtils.isBank(user)) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return OauthUserFactory.createOauthUser(user);
        }
    }
}
