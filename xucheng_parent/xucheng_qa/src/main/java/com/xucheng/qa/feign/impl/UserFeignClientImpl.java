package com.xucheng.qa.feign.impl;

import com.xucheng.qa.feign.UserFeignClient;
import entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/2/21 10:14
 * @Version: 1.0
 */
@Component
public class UserFeignClientImpl implements UserFeignClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String[] findAllTargetUser(String userid) {
        String[] strings= {};
        return null;
    }

    @Override
    public Result findById(String id) {
        return null;
    }
}
