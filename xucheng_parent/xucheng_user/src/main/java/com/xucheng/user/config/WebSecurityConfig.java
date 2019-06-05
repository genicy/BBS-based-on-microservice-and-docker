package com.xucheng.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        * authorizeRequests全配置注解的开端，说明需要的权限
        * 需要的权限分两部分，第一部分是拦截的路径，第二部分表示访问该路径的权限
        * antMatchers("/**").permitAll() 表示拦截什么路径，permitAll任何权限都可以访问，放行所有
        *anyRequest任何的请求，authenticated认证后才能访问
        * and().csrf().disable();固定写法，表示使csrf拦截失效
        * */
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
