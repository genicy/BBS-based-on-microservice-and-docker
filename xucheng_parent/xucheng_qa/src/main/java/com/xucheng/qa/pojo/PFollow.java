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
@Table(name="tb_pl")
public class PFollow implements Serializable {

    @Id
    private String id;

    /*用户id*/
    private String problemid;

    /*标签id*/
    private String labelid;

    public PFollow() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProblemid() {
        return problemid;
    }

    public void setProblemid(String problemid) {
        this.problemid = problemid;
    }

    public String getLabelid() {
        return labelid;
    }

    public void setLabelid(String labelid) {
        this.labelid = labelid;
    }

    public PFollow(String id, String problemid, String labelid) {
        this.id = id;
        this.problemid = problemid;
        this.labelid = labelid;
    }
}
