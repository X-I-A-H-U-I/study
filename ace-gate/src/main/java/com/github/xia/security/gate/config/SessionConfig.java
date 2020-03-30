//package com.github.xia.security.gate.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//
///**
// * <p>
// *   第一步：Spring配置负责创建Servlet过滤器，该过滤器用HttpSessionSpring Session支持的实现替换实现
// *   我们创建一个RedisConnectionFactory将Spring Session连接到Redis Server
// * </p>
// *
// * @author: xia
// * @date: 2020-03-14
// * @since: JDK 1.8
// * @version: 1.0
// */
//@Configuration
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
//public class SessionConfig {
//
////    @Bean
////    public LettuceConnectionFactory connectionFactory() {
////        return new LettuceConnectionFactory();
////    }
//}
