package com.github.xia.security.auth.service;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;

import java.util.UUID;

/**
 * 〈一句话功能简述〉 类<br>
 * 〈获取token随机数产生规则  api.v1 版本〉
 *
 * @author yag
 * @create 2019/1/7 0007 9:31
 * @since 1.0.0
 */
public class RandomAuthenticationKeyGenerator implements AuthenticationKeyGenerator {

    @Override
    public String extractKey(OAuth2Authentication authentication) {
        return UUID.randomUUID().toString();
    }
}
