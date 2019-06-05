package com.xucheng.sms.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/5/6 15:39
 * @Version: 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private RedisTemplate redisTemplate;
    /**
    * @Description: 当用户登录时，先检查redis中是否存在新消息
    * @Param: [rediskey]
    * @return: entity.Result
    * @Author: yangxucheng
    * @Date: 2019/5/6 15:43
    */
    @GetMapping("/getAllInfo/{rediskey}")
    public Result getAllInfo(@PathVariable String rediskey){
        Map<String, String> map = new HashMap<>();
        /*在redis中取map*/
        map = redisTemplate.opsForHash().entries(rediskey);
        System.out.print(map.size());
        redisTemplate.opsForHash().getOperations().delete(rediskey);
        return new Result(true, StatusCode.OK, "查询成功", map);

    }
}
