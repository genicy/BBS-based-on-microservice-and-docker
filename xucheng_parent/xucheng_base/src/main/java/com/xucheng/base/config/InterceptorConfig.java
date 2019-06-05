package com.xucheng.base.config;

import com.xucheng.base.interceptor.JwtlInteceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @introduction: xucheng_parent
 * @Description: 定义一个拦截器配置类，但此处是配置并非真正拦截，而是像aop一样，只做记录
 * @Author: yangxucheng
 * @Date: 2019/1/24 16:08
 * @Version: 1.0
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Autowired
    private JwtlInteceptor jwtlInteceptor;
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        /*
        *拦截所有路径
        *放过带有login的所有路径
        * */
        registry.addInterceptor(jwtlInteceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login/**");
    }
}
