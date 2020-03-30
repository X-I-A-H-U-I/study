package com.github.xia.security.jwt.secruity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *     相应封装，主要包含jwt token字段，直接返回对象即可
 * </p>
 *
 * @author: xia
 * @date: 2020-03-22
 * @since: JDK 1.8
 * @version: 1.0
 */
@Data
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = -2329619657123318192L;
    private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

}
