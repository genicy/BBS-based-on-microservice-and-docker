package com.xucheng.user.dao;

import com.xucheng.user.pojo.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/3/16 16:27
 * @Version: 1.0
 */
public interface FollowDao extends JpaRepository<Follow, String>, JpaSpecificationExecutor<Follow> {
    @Query(value = "SELECT * FROM `tb_follow` where userid=?", nativeQuery = true)
    public List<Follow> findTargetUser(String userid);
}
