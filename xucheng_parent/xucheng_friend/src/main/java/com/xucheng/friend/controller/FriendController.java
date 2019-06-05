package com.xucheng.friend.controller;

import com.xucheng.friend.feign.UserFeignClient;
import com.xucheng.friend.service.FriendService;
import com.xucheng.friend.service.NoFriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/1/25 14:30
 * @Version: 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private NoFriendService noFriendService;
    @Autowired
    private HttpServletRequest request;

    /*这是交友模块，喜欢一个人followcount+1, 关注一个专栏作者followcount也+1*/
    @PutMapping("/like/{friendid}/{type}")
    public Result addFriend(@PathVariable String friendid,@PathVariable String type){
        if(type!=null&&!type.equals("")){
            //从拦截器获得验证对象
            Claims claims = (Claims)request.getAttribute("claims_user");
            if(claims==null){
                return new Result(false, StatusCode.ERROR,"权限不足");
            }
            else if(type.equals("1")){
                friendService.addFriend(claims,friendid);
                return new Result(true, StatusCode.OK,"添加成功");

            }
            else if(type.equals("0")){
                noFriendService.addNoFriend(claims,friendid);
                return new Result(true, StatusCode.OK,"添加成功");
            }
            else {
                return new Result(false, StatusCode.ERROR,"参数有误");
            }
        }else {
            return new Result(false, StatusCode.ERROR,"缺少参数");
        }
    }
}
