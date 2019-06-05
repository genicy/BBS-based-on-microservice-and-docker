package com.xucheng.user.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/3/16 16:19
 * @Version: 1.0
 */
@Entity
@Table(name="tb_follow")
public class Follow implements Serializable {

    @Id
    private String id;

    private String userid;

    private String targetuser;

    public Follow() {
    }

    public Follow(String id, String userid, String targetuser) {
        this.id = id;
        this.userid = userid;
        this.targetuser = targetuser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTargetuser() {
        return targetuser;
    }

    public void setTargetuser(String targetuser) {
        this.targetuser = targetuser;
    }
}
