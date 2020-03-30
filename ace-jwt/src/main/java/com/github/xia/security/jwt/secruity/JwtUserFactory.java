package com.github.xia.security.jwt.secruity;

import com.github.xia.security.common.vo.ClientInfo;
import com.github.xia.security.common.vo.UserInfo;
import com.github.xia.security.jwt.vo.JwtUser;

import java.util.Date;

/**
 * <p>
 *     创建JWT用户工厂
 * </p>
 *
 * @author: xia
 * @date: 2020-03-23
 * @since: JDK 1.8
 * @version: 1.0
 */
public final class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(ClientInfo info) {
        return new JwtUser(
                info.getId()+"",
                info.getCode(),
                info.getSecret(),
                null,
                new Date(), info.isLocked());
    }

    public static JwtUser createJwtUser(UserInfo info) {
        return new JwtUser(
                info.getId()+"",
                info.getUsername(),
                info.getPassword(),
                null,
                new Date(), Boolean.FALSE);
    }


}
