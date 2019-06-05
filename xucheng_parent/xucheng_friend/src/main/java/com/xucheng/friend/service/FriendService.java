package com.xucheng.friend.service;

import com.xucheng.friend.dao.FriendDao;
import com.xucheng.friend.feign.UserFeignClient;
import com.xucheng.friend.pojo.Friend;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.JwtUtil;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/1/25 14:51
 * @Version: 1.0
 */
@Service
@Transactional
public class FriendService {
    @Autowired
    private UserFeignClient feignClient;
    @Autowired
    private FriendDao friendDao;
    public void addFriend(Claims claims, String friendid) {
            //从拦截器获取用户id
            String userid = (String) claims.getId();
            Friend friend = friendDao.findByFriendidAndAndUserid(friendid,userid);
            Friend friend1 = friendDao.findByFriendidAndAndUserid(userid,friendid);
            /*判断以前是否以前添加过*/
            if(friend==null){
                friend =new Friend(userid,friendid,"0");
                friendDao.save(friend);
                /*调用用户微服务同步更新粉丝数与关注数*/
                feignClient.updateFollowCountAndFansCount(userid,friendid,1);
                /*判断对方是否也喜欢你*/
                if(friend1!=null){
                    friendDao.addFriend("1",userid,friendid);
                    friendDao.addFriend("1",friendid,userid);
                }
            }else {
                throw new RuntimeException("重复添加");
            }
    }

}
