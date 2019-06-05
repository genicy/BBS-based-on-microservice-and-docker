package com.xucheng.friend.feign;

import com.xucheng.friend.feign.impl.UserFeignClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @introduction: xucheng_parent
 * @Description: 调用用户微服务更新关注数与粉丝数
 * @Author: yangxucheng
 * @Date: 2019/1/28 11:17
 * @Version: 1.0
 */
@FeignClient(value = "xucheng-user",fallback = UserFeignClientImpl.class)
public interface UserFeignClient {
    /*必须在PathVariable中指明参数名*/
    @PutMapping("/user/{userid}/{friendid}/{x}")
    public void updateFollowCountAndFansCount(@PathVariable("userid") String userid, @PathVariable("friendid") String friendid, @PathVariable("x") int x);
}
