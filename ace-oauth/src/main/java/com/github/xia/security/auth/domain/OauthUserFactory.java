package com.github.xia.security.auth.domain;

import com.github.xia.security.common.vo.ClientInfo;
import com.github.xia.security.common.vo.UserInfo;
import java.util.Date;

/**
 * <p>
 *     创建OauthUser用户工厂
 * </p>
 *
 * @author: xia
 * @date: 2020-03-23
 * @since: JDK 1.8
 * @version: 1.0
 */
public final class OauthUserFactory {
    private OauthUserFactory() {
    }

    public static OauthUser create(ClientInfo info) {
        return new OauthUser(
                info.getId()+"",
                info.getCode(),
                info.getSecret(),
                null,
                new Date(), info.isLocked());
    }

    public static OauthUser createOauthUser(UserInfo info) {
        return new OauthUser(
                info.getId()+"",
                info.getUsername(),
                info.getPassword(),
                null,
                new Date(), Boolean.FALSE);
    }


}
