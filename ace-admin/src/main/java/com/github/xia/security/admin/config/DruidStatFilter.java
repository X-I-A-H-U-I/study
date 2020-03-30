package com.github.xia.security.admin.config;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * @explain：
 * @author: XIA
 * @date: 2020-03-12
 * @since: JDK 1.8
 * @version: 1.0
 */
@WebFilter(filterName="druidWebStatFilter",urlPatterns="/druid/*",
        initParams={
                @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
        })
public class DruidStatFilter extends WebStatFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setContentType("text/html");
        super.doFilter(request, response, chain);
    }
}
