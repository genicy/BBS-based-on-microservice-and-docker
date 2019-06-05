package com.xucheng.article.dao;

import com.xucheng.article.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface CommentDao extends JpaRepository<Comment,String>,JpaSpecificationExecutor<Comment>{

    @Modifying //可能出现线程问题的操作均需加modifying，即增删改
    @Query(value = "UPDATE tb_comment set thumbup=thumbup+1 where id=?1",nativeQuery = true)//1表示参数列表第一个变量
    void thumbup(String commentId);;
}
