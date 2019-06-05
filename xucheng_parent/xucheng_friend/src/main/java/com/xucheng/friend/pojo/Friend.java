package com.xucheng.friend.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/1/25 14:31
 * @Version: 1.0
 */
@Entity
@Table(name="tb_friend")
@IdClass(Friend.class)
public class Friend implements Serializable {
    @Id
    private String userid;
    @Id
    private String friendid;

    private String islike;

    public Friend() {
    }

    public Friend(String userid, String friendid, String islike) {
        this.userid = userid;
        this.friendid = friendid;
        this.islike = islike;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }

    public String getIslike() {
        return islike;
    }

    public void setIslike(String islike) {
        this.islike = islike;
    }
}
