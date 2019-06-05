package com.xucheng.friend.dao;

import com.xucheng.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/1/25 14:59
 * @Version: 1.0
 */
public interface FriendDao extends JpaRepository<Friend,String> {
    public Friend findByFriendidAndAndUserid(String friendid,String userid);

    @Modifying/*所有可能会产生线程安全的问题*/
    @Query(value = "update tb_friend set islike=? where userid=? and friendid= ?" ,nativeQuery = true)
    public void addFriend(String isLike,String userid,String friendid);
}
