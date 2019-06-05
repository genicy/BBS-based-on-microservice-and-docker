package com.xucheng.article.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class StateResourceConfigurer extends WebMvcConfigurationSupport {

    /**
    * @Description: 配置访问静态资源 ,由于上传图片后每次都需要重启才能访问，故配置虚拟路径
    * @Param: [registry]
    * @return: void
    * @Author: yangxucheng
    * @Date: 2019/3/5 10:12
    */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        System.out.println("虚拟路径配置生效");
        registry.addResourceHandler("/articles/**").addResourceLocations("file:/D:/github/xucheng_parent/images/articles/");
        registry.addResourceHandler("/cover/**").addResourceLocations("file:/D:/github/xucheng_parent/images/cover/");
        super.addResourceHandlers(registry);
    }
}
