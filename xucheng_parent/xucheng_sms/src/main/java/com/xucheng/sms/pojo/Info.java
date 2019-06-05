package com.xucheng.sms.pojo;

/**
 * @introduction: 为定义的消息格式，分别为昵称 动作 对象；如张三 评论了你的文章 如何学习...
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/5/7 14:33
 * @Version: 1.0
 */
public class Info {
    private String nickname;
    private String action;
    private String object;

    public Info(String nickname, String action, String object) {
        this.nickname = nickname;
        this.action = action;
        this.object = object;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Info() {
    }
}
