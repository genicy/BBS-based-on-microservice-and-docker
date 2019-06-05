package com.xucheng.friend.feign.impl;

import com.xucheng.friend.feign.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/2/21 10:14
 * @Version: 1.0
 */
public class UserFeignClientImpl implements UserFeignClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void updateFollowCountAndFansCount(String userid, String friendid, int x) {
        logger.info("交友微服务熔断了");
    }
}
