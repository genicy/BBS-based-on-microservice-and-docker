package com.xucheng.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xucheng.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{
    public User findByMobile(String mobile);
    public User findByLoginname(String Loginname);

    /**
    * @Description:可能导致线程问题的添加Modifying
    */
    @Modifying
    @Query(value ="UPDATE tb_user set followcount=followcount+? where id=?" ,nativeQuery = true)
    void updateFollowCount(int x,String userid);

    @Modifying
    @Query(value ="UPDATE tb_user set fanscount=fanscount+? where id=?" ,nativeQuery = true)
    void updateFansCount(int x, String friendid);
}
