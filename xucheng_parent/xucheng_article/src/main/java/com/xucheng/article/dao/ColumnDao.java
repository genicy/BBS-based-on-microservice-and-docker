package com.xucheng.article.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xucheng.article.pojo.Column;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ColumnDao extends JpaRepository<Column,String>,JpaSpecificationExecutor<Column>{

    /*select *, round(rand())*5 as randnum from tb_column首先查询出5之内的随机数作为新的属性
    * select c.id, c.`name` , c.summary, c.state, c.userid, c.createtime, c.checktime from () as c where userid not in(:followIdlist) order by c.randnum asc limit 5
    * 第二个根据随机数排序找出五个columndu对象（不含以下id），并排除新属性
    * */
    @Query(value = "select c.id, c.`name` , c.summary, c.state, c.userid, c.createtime, c.checktime from (select *, round(rand())*5 as randnum from tb_column) as c where state='1' and userid not in(:followIdlist) order by c.randnum asc limit 5", nativeQuery = true)
    List<Column> getRandColumn(@Param(value = "followIdlist") List<String> followIdlist);
	
}
