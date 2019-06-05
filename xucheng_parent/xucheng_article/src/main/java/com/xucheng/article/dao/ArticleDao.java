package com.xucheng.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xucheng.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */


public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{
    @Modifying //可能出现线程问题的操作均需加modifying，即增删改
    @Query(value = "UPDATE tb_article set thumbup=thumbup+1 where id=?1",nativeQuery = true)//1表示参数列表第一个变量
	void thumbup(String articleId);

    @Modifying
    @Query(value = "UPDATE tb_article set state=1 where id=?" ,nativeQuery = true)
	void examine(String articleId);
}
