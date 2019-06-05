package com.xucheng.qa.feign;

import com.xucheng.qa.feign.impl.BaseClientImpl;
import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @introduction: xucheng_parent
 * @Description: 使用feign可以通过rest方式+接口声明完成微服务之间的调用
 * @Author: yangxucheng
 * @Date: 2019/1/25 10:43
 * @Version: 1.0
 */
@FeignClient(value = "xucheng-base",fallback = BaseClientImpl.class)
public interface BaseClient {

    @GetMapping("/label/{labelid}")
    Result findById(@PathVariable("labelid") String labelid);

    @PutMapping("/label/follow/{id}/{follow}")
    Result updateFans(@PathVariable(value="id") String id, @PathVariable(value = "follow") int follow);
}
