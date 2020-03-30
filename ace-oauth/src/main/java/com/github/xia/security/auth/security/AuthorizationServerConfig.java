package com.github.xia.security.auth.security;

import com.github.xia.security.auth.domain.LoginUser;
import com.github.xia.security.auth.service.AuthUserDetailService;
import com.github.xia.security.auth.service.RandomAuthenticationKeyGenerator;
import com.github.xia.security.auth.service.RedisAuthorizationCodeService;
import com.github.xia.security.auth.service.RedisClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 认证服务认证、授权配置类
 * </p>
 *
 * @author: xia
 * @date: 2020-03-27
 * @since: JDK 1.8
 * @version: 1.0
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthUserDetailService authUserDetailService;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private RedisAuthorizationCodeService redisAuthorizationCodeService;

    @Autowired
    private RedisClientDetailsService redisClientDetailsService;

    /**
     * 使用jwt或者redis<br>
     * 默认redis
     */
    @Value("${access_token.store-jwt:false}")
    private boolean storeWithJwt;

    /**
     * token默认失效时间 30分钟30*60秒
     */
    @Value("${access_token.expires_in:1800}")
    private int tokenExpiresIn;
    /**
     * token默认失效时间 2小时分钟2*60*60秒
     */
    @Value("${refresh_token.expires_in:7200}")
    private int refreshExpiresIn;
    /**
     * 登陆后返回的json数据是否追加当前用户信息<br>
     * 默认falseOA
     */
    @Value("${access_token.add-userinfo:true}")
    private boolean addUserInfo;

    /**
     * jwt签名key，可随意指定<br>
     * 如配置文件里不设置的话，冒号后面的是默认值
     */
    @Value("${access_token.jwt-signing-key:cloud}")
    private String signingKey;

    /**
     * token保存策略【自定义token】
     * tokenStore类型：JdbcTokenStore、JwtTokenStore、RedisTokenStore
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        //数据库模式
        //TokenStore tokenStore =new JdbcTokenStore(dataSource);
        //jwt或者redis模式
        if (storeWithJwt) {
            return new JwtTokenStore(accessTokenConverter());
        }
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setAuthenticationKeyGenerator(new RandomAuthenticationKeyGenerator());
        return redisTokenStore;
    }

    @Primary
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        //设置获取的TOKEN 失效日期 默认半小时
        defaultTokenServices.setAccessTokenValiditySeconds(tokenExpiresIn);
        //设置刷新TOKEN 失效时间 默认2个小时
        defaultTokenServices.setRefreshTokenValiditySeconds(refreshExpiresIn);
        //设置是否支持允许刷新TOKEN，开启
        defaultTokenServices.setSupportRefreshToken(true);
        //设置是否重新使用原来的刷新TOKEN 此处 关闭
        defaultTokenServices.setReuseRefreshToken(false);
        //自定义token 键值
        defaultTokenServices.setTokenStore(tokenStore());
        //如果使用redis 开启 令牌增强
        //配置tokenEhancer（令牌增强器） 自定义加入登录用户信息
        if (!storeWithJwt) {
            defaultTokenServices.setTokenEnhancer((accessToken, authentication) -> {
                addLoginUserInfo(accessToken, authentication);
                return accessToken;
            });
        }
        return defaultTokenServices;
    }

    /**
     * 授权信息保存策略【数据库模式】
     *
     * @return
     */
    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    /**
     * 授权码模式专用对象【数据库模式】
     *
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * 指定客户端登录信息来源【可以基于内存或者jdbc】
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //从redis获取
        clients.withClientDetails(redisClientDetailsService);
        //存入缓存
        redisClientDetailsService.loadAllClientToCache();
        //从数据库取数据
        //clients.withClientDetails(clientDetailsService());
        // 从内存中取数据
//        clients.inMemory()
//                .withClient("baidu")
//                .secret(passwordEncoder.encode("12345"))
//                .resourceIds("product_api")
//                .authorizedGrantTypes(
//                        "authorization_code",
//                        "password",
//                        "client_credentials",
//                        "implicit",
//                        "refresh_token"
//                )// 该client允许的授权类型 authorization_code,password,refresh_token,implicit,client_credentials
//                .scopes("read", "write")// 允许的授权范围
//                .autoApprove(false)
//                //加上验证回调地址
//                .redirectUris("http://www.baidu.com");
    }

    /**
     * 从数据库查询资源客户端信息
     *
     * @return
     */
    @Bean
    public JdbcClientDetailsService clientDetailsService() {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
        return jdbcClientDetailsService;
    }

    /**
     * 检测token的策略、允许表单验证，浏览器直接发送post请求即可获取token
     *
     * @param oauthServer
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        //允许form表单客户端认证,允许客户端使用client_id和client_secret获取token
        oauthServer.allowFormAuthenticationForClients()
                //通过验证返回token信息
                .checkTokenAccess("isAuthenticated()")
                // 获取token请求不进行拦截
                .tokenKeyAccess("permitAll()")
                .passwordEncoder(passwordEncoder);
    }

    /**
     * OAuth2的主配置信息
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenServices(tokenServices())
                .authorizationCodeServices(redisAuthorizationCodeService);
                //数据库模式
                //.approvalStore(approvalStore())
                //.authorizationCodeServices(authorizationCodeServices())
                //.tokenStore(tokenStore())
                //.userDetailsService(authUserDetailService);
    }

    /**
     * Jwt资源令牌转换器<br>
     * 参数access_token.store-jwt为true时用到
     *
     * @return accessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                OAuth2AccessToken oAuth2AccessToken = super.enhance(accessToken, authentication);
                // 2018.07.13 将当前用户信息追加到登陆后返回数据里
                addLoginUserInfo(oAuth2AccessToken, authentication);
                return oAuth2AccessToken;
            }
        };
        DefaultAccessTokenConverter defaultAccessTokenConverter = (DefaultAccessTokenConverter) jwtAccessTokenConverter
                .getAccessTokenConverter();
        DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();
        userAuthenticationConverter.setUserDetailsService(authUserDetailService);

        defaultAccessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
        jwtAccessTokenConverter.setSigningKey(signingKey);

        return jwtAccessTokenConverter;
    }

    /**
     * 将当前用户信息追加到登陆后返回的json数据里<br>
     * 通过参数access_token.add-userinfo控制<br>
     *
     * @param accessToken
     * @param authentication
     */
    private void addLoginUserInfo(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (!addUserInfo) {
            return;
        }

        if (accessToken instanceof DefaultOAuth2AccessToken) {
            DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
            Authentication userAuthentication = authentication.getUserAuthentication();
            Object principal = userAuthentication.getPrincipal();
            if (principal instanceof LoginUser) {
                LoginUser loginUser = (LoginUser) principal;
                //清空密码 不传递到前端
                loginUser.setPassword(null);
                // 旧的附加参数
                Map<String, Object> map = new HashMap<>(defaultOAuth2AccessToken.getAdditionalInformation());
                // 追加当前登陆用户
                map.put("loginUser", loginUser);
                log.info("map:", map);
                defaultOAuth2AccessToken.setAdditionalInformation(map);
                //重置失效时间
                defaultOAuth2AccessToken.setExpiration(new Date(System.currentTimeMillis() + tokenExpiresIn * 1000L));
                log.info("defaultOAuth2AccessToken:", defaultOAuth2AccessToken);
            }
        }
    }
}

