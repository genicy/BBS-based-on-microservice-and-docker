package com.xucheng.qa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xucheng.qa.pojo.Reply;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ReplyDao extends JpaRepository<Reply,String>,JpaSpecificationExecutor<Reply>{
    @Modifying //可能出现线程问题的操作均需加modifying，即增删改
    @Query(value = "UPDATE tb_reply set thumbup=thumbup+?2 where id=?1",nativeQuery = true)//1表示参数列表第一个变量
    void thumbup(String commentId, int thumbup);;
}
