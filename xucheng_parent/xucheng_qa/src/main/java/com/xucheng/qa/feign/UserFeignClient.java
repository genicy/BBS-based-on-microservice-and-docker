package com.xucheng.qa.feign;

import com.xucheng.qa.feign.impl.UserFeignClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/3/16 16:34
 * @Version: 1.0
 */
@FeignClient(value = "xucheng-user",fallback = UserFeignClientImpl.class)
public interface UserFeignClient {
    @PostMapping("/user/findAllTargetUser")
    public String[] findAllTargetUser(@RequestBody String userid);

    @RequestMapping(value="/user/{id}",method= RequestMethod.GET)
    public Result findById(@PathVariable("id") String id);
}
