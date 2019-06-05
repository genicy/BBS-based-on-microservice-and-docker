package com.xucheng.qa.dao;

import com.xucheng.qa.pojo.UFollow;
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
public interface UFollowDao extends JpaRepository<UFollow, String>, JpaSpecificationExecutor<UFollow> {
    @Modifying
    @Query(value = "delete from tb_ul where uid=?1 and lid=?2", nativeQuery = true)
    void deleteUfollow(String userid,String labelid);

    List<UFollow> findAllByUid(String uid);
}
