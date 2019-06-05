package com.xucheng.spit.dao;

import com.xucheng.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @introduction: xucheng_parent
 * @Description: 使用mongoDB作为数据库
 * @Author: yangxucheng
 * @Date: 2019/1/21 14:39
 * @Version: 1.0
 */
public interface SpitDao extends MongoRepository<Spit, String> {
    public Page<Spit> findByParentid(String id, Pageable pageable);
}
