package com.xucheng.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/1/22 14:56
 * @Version: 1.0
 */

@Document(indexName = "xucheng_article",type = "article")//索引库名称（类比数据库）与类型名称（类比表）
public class Article implements Serializable {
    @Id
    private String id;
    //是否索引，能否被搜索
    //是否分词，整体还是单词匹配
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer ="ik_max_word" )
    private String title;
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer ="ik_max_word" )
    private String content;
    private String state;
    private String image;
    public Article(String id, String title, String content, String state, String image) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.state = state;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Article() {
    }

    public Article(String id, String title, String content, String state) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", state='" + state + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
