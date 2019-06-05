package com.xucheng.article;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import util.IdWorker;
import util.JwtUtil;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class ArticleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticleApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}

	@Bean
	public JwtUtil jwtUtil(){
		return new JwtUtil();
	}
	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation("d://temp");
		return factory.createMultipartConfig();
	}
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
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			System.out.println("虚拟路径配置生效");
			registry.addResourceHandler("/articles/**").addResourceLocations("file:/D:/github/xucheng_parent/images/articles/");
			registry.addResourceHandler("/cover/**").addResourceLocations("file:/D:/github/xucheng_parent/images/cover/");
			super.addResourceHandlers(registry);
		}
	}

}
