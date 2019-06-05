package com.xucheng.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
import util.JwtUtil;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/1/16 17:40
 * @Version: 1.0
 */
@SpringBootApplication
@EnableEurekaClient
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class,args);
    }
    @Bean
    public IdWorker getIdWorker(){
        return new IdWorker(1,1);
    }
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
}
