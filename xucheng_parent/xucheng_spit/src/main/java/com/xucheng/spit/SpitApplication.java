package com.xucheng.spit;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @introduction: xucheng_parent
 * @Description: 吐槽模块
 * @Author: yangxucheng
 * @Date: 2019/1/21 14:17
 * @Version: 1.0
 */
@SpringBootApplication
@EnableEurekaClient
public class SpitApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpitApplication.class,args);
    }
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
