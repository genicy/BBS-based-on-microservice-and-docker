package com.xucheng.friend.service;

import com.xucheng.friend.dao.FriendDao;
import com.xucheng.friend.dao.NoFriendDao;
import com.xucheng.friend.pojo.Friend;
import com.xucheng.friend.pojo.NoFriend;
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
public class NoFriendService {
    @Autowired
    private NoFriendDao noFriendDao;

    /*不喜欢*/
    public void addNoFriend(Claims claims, String nofriendid) {
        //从拦截器获取用户id
        String userid = (String) claims.getId();
        NoFriend nofriend = noFriendDao.findByFriendidAndAndUserid(nofriendid,userid);
        if(nofriend==null){
            nofriend = new NoFriend(userid,nofriendid);
            noFriendDao.save(nofriend);
        }else {
            throw new RuntimeException("您已经踩过了");
        }
    }
}
