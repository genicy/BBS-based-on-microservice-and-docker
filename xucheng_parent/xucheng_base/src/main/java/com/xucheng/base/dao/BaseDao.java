package com.xucheng.base.dao;

import com.xucheng.base.pojo.Label;
import entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @introduction: xucheng_parent
 * @Description: JpaRepository实现Jpa的操作，JpaSpecificationExecutor支持动态查询分页
 * @Author: yangxucheng
 * @Date: 2019/1/16 19:55
 * @Version: 1.0
 */
public interface BaseDao extends JpaRepository<Label,String>, JpaSpecificationExecutor<Label> {
    @Query(value = "SELECT type FROM `tb_label` GROUP BY type",nativeQuery = true)
    List<String> findGroupByType();

}
