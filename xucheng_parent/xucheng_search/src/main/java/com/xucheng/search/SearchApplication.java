package com.xucheng.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @introduction: 通过elasticsearch提供搜索服务
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/1/22 11:25
 * @Version: 1.0
 */
@SpringBootApplication
@EnableEurekaClient
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
