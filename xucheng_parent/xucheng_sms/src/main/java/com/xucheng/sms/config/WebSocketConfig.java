package com.xucheng.sms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @introduction: xucheng_parent
 * @Description: 开启websocket的支持
 * @Author: yangxucheng
 * @Date: 2019/5/6 10:53
 * @Version: 1.0
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
