package com.xucheng.recruit.dao;

import com.xucheng.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

    List<Recruit> findTop6ByStateOrderByCreatetimeDesc(String state);
    List<Recruit> findTop6ByStateNotOrderByCreatetimeDesc(String state);
}
