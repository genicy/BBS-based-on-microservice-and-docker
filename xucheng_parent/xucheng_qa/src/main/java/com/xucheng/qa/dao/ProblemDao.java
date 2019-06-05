package com.xucheng.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xucheng.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
/*    @Query(value = "SELECT tb_problem.id,tb_problem.content,tb_problem.createtime,tb_problem.nickname,tb_problem.reply,tb_problem.replyname,tb_problem.replytime,tb_problem.solve,tb_problem.thumbup,tb_problem.title,tb_problem.updatetime,tb_problem.userid,tb_problem.visits from tb_problem,tb_pl where tb_pl.labelid = ? and tb_problem.id = tb_pl.problemid ORDER BY replytime DESC", nativeQuery = true)
    Page<Problem> newlist(String labelId, Pageable pageable);

    @Query(value = "SELECT tb_problem.id,tb_problem.content,tb_problem.createtime,tb_problem.nickname,tb_problem.reply,tb_problem.replyname,tb_problem.replytime,tb_problem.solve,tb_problem.thumbup,tb_problem.title,tb_problem.updatetime,tb_problem.userid,tb_problem.visits from tb_problem,tb_pl where tb_pl.labelid = ? and tb_problem.id = tb_pl.problemid ORDER BY reply DESC", nativeQuery = true)
    Page<Problem> hotlist(String labelId, Pageable pageable);

    @Query(value = "SELECT tb_problem.id,tb_problem.content,tb_problem.createtime,tb_problem.nickname,tb_problem.reply,tb_problem.replyname,tb_problem.replytime,tb_problem.solve,tb_problem.thumbup,tb_problem.title,tb_problem.updatetime,tb_problem.userid,tb_problem.visits from tb_problem,tb_pl where tb_pl.labelid = ? and tb_problem.id = tb_pl.problemid and reply=0 ORDER BY createtime DESC", nativeQuery = true)
    Page<Problem> waitlist(String labelId, Pageable pageable);

    @Query(value = "SELECT * from tb_problem ORDER BY replytime DESC", nativeQuery = true)
    Page<Problem> newNoLablelist(Pageable pageable);

    @Query(value = "SELECT * from tb_problem ORDER BY reply DESC", nativeQuery = true)
    Page<Problem> hotNoLablelist(Pageable pageable);

    @Query(value = "SELECT * from tb_problem where reply=0 ORDER BY createtime DESC", nativeQuery = true)
    Page<Problem> waitNoLablelist(Pageable pageable);*/



}
