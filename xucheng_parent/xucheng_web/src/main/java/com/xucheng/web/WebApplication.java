package com.xucheng.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

/**
 * @introduction: xucheng_parent
 * @Description: 网站前台网关，客户端调用时使用
 * @Author: yangxucheng
 * @Date: 2019/2/21 10:44
 * @Version: 1.0
 */
@EnableZuulProxy
@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class,args);
    }
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("d://temp");
        return factory.createMultipartConfig();
    }
}
