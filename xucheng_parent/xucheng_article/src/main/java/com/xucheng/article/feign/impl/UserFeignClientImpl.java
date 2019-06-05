package com.xucheng.article.feign.impl;

import com.xucheng.article.feign.UserFeignClient;
import entity.Result;
import entity.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.plugin2.message.Message;

import java.util.List;

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
    public String[] findAllTargetUser(String userid) {
        return null;
    }

    @Override
    public Result findById(String id) {
        return null;
    }
}
