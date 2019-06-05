package com.xucheng.sms.listener;

import com.xucheng.sms.utils.SendMsg_webchinese;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/1/23 11:50
 * @Version: 1.0
 */
@RabbitListener(queues = "sms")
@Component
public class SmsListener {
    @Autowired
    private SendMsg_webchinese sendMsg_webchinese;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @RabbitHandler
    public void sendSms(Map<String,String> map){
        String code = map.get("checkcode");
        String mobile = map.get("mobile");
        try {
            sendMsg_webchinese.sendSms(mobile,code);
            logger.info(mobile+ ": "+code);
        } catch (Exception e) {
            logger.info(mobile+ ": 验证码发送失败");
            e.printStackTrace();
        }
    }
}
