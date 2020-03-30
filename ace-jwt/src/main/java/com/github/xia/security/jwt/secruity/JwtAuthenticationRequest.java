package com.github.xia.security.jwt.secruity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *     请求封装，主要包含username和password字段
 * </p>
 *
 * @author: xia
 * @date: 2020-03-22
 * @since: JDK 1.8
 * @version: 1.0
 */
@Data
public class JwtAuthenticationRequest implements Serializable {

    private static final long serialVersionUID = -3869561538462721163L;
    private String clientId;
    private String secret;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String clientId, String secret) {
        this.setClientId(clientId);
        this.setSecret(secret);
    }
}
