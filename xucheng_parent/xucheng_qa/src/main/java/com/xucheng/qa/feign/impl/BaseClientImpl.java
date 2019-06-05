package com.xucheng.qa.feign.impl;

import com.xucheng.qa.feign.BaseClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/2/21 9:55
 * @Version: 1.0
 */
@Component
public class BaseClientImpl implements BaseClient {
    @Override
    public Result findById(String labelid) {
        return new Result(false, StatusCode.ERROR,"服务器熔断了");
    }

    @Override
    public Result updateFans(String id, int follow) {
        return new Result(false, StatusCode.ERROR,"base服务与qa服务器熔断");
    }
}
