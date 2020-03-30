package com.github.xia.security.jwt.auth;

import com.github.xia.security.jwt.secruity.JwtAuthenticationRequest;
import com.github.xia.security.jwt.secruity.JwtAuthenticationResponse;
import com.github.xia.security.jwt.secruity.JwtTokenUtil;
import com.github.xia.security.jwt.secruity.JwtUserDetailsService;
import com.github.xia.security.jwt.vo.JwtUser;
import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 登陆/查看/刷新token
 * </p>
 *
 * @author: xia
 * @date: 2020-03-22
 * @since: JDK 1.8
 * @version: 1.0
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    /**
     * 生成token
     *
     * @param authenticationRequest
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "/token", method = RequestMethod.POST,produces="application/json;charset=utf-8")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
        final String token = authService.login(authenticationRequest.getClientId(), authenticationRequest.getSecret());
        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    /**
     * 刷新token
     *
     * @param request
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "refresh", method = RequestMethod.GET,produces="application/json;charset=utf-8")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if (refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }

    @RequestMapping(value="/jwtUser",method = RequestMethod.GET,produces="application/json;charset=utf-8")
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        JwtUser jwtUser = authService.getJwtUserByToken(token);
        return jwtUser;
    }

    @RequestMapping(value="/hi",method = RequestMethod.GET,produces="application/json;charset=utf-8")
    public String hi(){
        System.out.println("hi................");
        return "hi";
    }
}
