package com.xucheng.qa.dao;

import com.xucheng.qa.pojo.PFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/** 
* @Description:  
* @Param:  
* @return:  
* @Author: yangxucheng 
* @Date: 2019/3/28 16:31
*/ 
public interface PFollowDao extends JpaRepository<PFollow, String>, JpaSpecificationExecutor<PFollow> {

    List<PFollow> findAllByProblemid(String problemid);

    List<PFollow> findAllByLabelid(String labelid);
}
