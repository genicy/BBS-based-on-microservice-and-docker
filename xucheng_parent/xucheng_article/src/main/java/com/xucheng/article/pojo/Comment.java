package com.xucheng.article.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_comment")
public class Comment implements Serializable{

	@Id
	private String id;//


	
	private String articleid;//
	private String userid;//
	private String comment;//
	private Date createtime;//
	private String replyid;//向谁回复
	private String taskid;//层主id（会包含多级评论）
	private String state;//评论状态
	private int thumbup;

	public int getThumbup() {
		return thumbup;
	}

	public Comment() {
	}

	public Comment(String id, String articleid, String userid, String comment, Date createtime, String replyid, String taskid, String state, int thumbup) {
		this.id = id;
		this.articleid = articleid;
		this.userid = userid;
		this.comment = comment;
		this.createtime = createtime;
		this.replyid = replyid;
		this.taskid = taskid;
		this.state = state;
		this.thumbup = thumbup;
	}

	public void setThumbup(int thumbup) {
		this.thumbup = thumbup;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getArticleid() {		
		return articleid;
	}
	public void setArticleid(String articleid) {
		this.articleid = articleid;
	}

	public String getUserid() {		
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getComment() {		
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getReplyid() {		
		return replyid;
	}
	public void setReplyid(String replyid) {
		this.replyid = replyid;
	}

	public String getTaskid() {		
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}


	
}
