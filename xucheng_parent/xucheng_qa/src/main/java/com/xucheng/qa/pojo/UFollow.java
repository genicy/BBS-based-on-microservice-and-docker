package com.xucheng.qa.pojo;

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
@Table(name="tb_ul")
public class UFollow implements Serializable {

    @Id
    private String id;

    /*用户id*/
    private String uid;

    /*标签id*/
    private String lid;

    public UFollow() {
    }

    public UFollow(String id, String uid, String lid) {
        this.id = id;
        this.uid = uid;
        this.lid = lid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getuid() {
        return uid;
    }

    public void setuid(String uid) {
        this.uid = uid;
    }

    public String getlid() {
        return lid;
    }

    public void setlid(String lid) {
        this.lid = lid;
    }
}
