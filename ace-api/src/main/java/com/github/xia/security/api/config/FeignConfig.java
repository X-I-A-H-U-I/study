package com.github.xia.security.api.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * <p>
 *     feign调用返回string乱码
 * </p>
 *
 * @author: xia
 * @date: 2020-03-19
 * @since: JDK 1.8
 * @version: 1.0
 */
@Configuration
public class FeignConfig {
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

//    @Bean
//    public Encoder feignFormEncoder() {
//        return new SpringFormEncoder(new SpringEncoder(messageConverters));
//    }

    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder( () -> {
            FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter() {
                @Override
                public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
                    Object result=null;
                    if ("java.lang.String".equals(type.getTypeName())) {
                        result= StreamUtils.copyToString(inputMessage.getBody(), Charset.forName("utf8"));
                    } else {
                        result=super.read(type, contextClass, inputMessage);
                    }
                    return result;
                }
            };
            return new HttpMessageConverters(fastConverter);
        }));
    }

}
