package com.xucheng.sms.listener;

import com.xucheng.sms.utils.SendMsg_webchinese;
import com.xucheng.sms.websocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/5/6 10:34
 * @Version: 1.0
 */

@RabbitListener(queues = "info")
@Component
public class InfoListener {
    @Autowired
    private WebSocketServer webSocketServer;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @RabbitHandler
    public void sendInfo(Map<String,String> map){
        String content = map.get("content");
        String[] strings =content.split(";");
        logger.info(content);
        try {
            /*sid为唯一标识，判断发送给哪个用户*/
            webSocketServer.sendInfo(content, strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
