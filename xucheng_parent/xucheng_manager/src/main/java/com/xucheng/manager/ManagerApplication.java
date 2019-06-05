package com.xucheng.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import util.JwtUtil;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * @introduction: xucheng_parent
 * @Description: 网站后台网关，管理员使用，与用户端不同，后台访问任何请求路径都得验证，所以在网关分发请求时便进行验证
 * 用户端具体请求再进行验证
 * @Author: yangxucheng
 * @Date: 2019/2/21 10:44
 * @Version: 1.0
 */
@EnableZuulProxy
@SpringBootApplication
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }

    /*网关进行验证*/
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }


    /*spring boot 内部上传文件临时存储路径不存在，手动添加*/
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location = System.getProperty("user.dir") + "/data/tmp";
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }

}
