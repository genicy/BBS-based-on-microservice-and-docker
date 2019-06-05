package com.xucheng.spit.service;

import com.xucheng.spit.dao.SpitDao;
import com.xucheng.spit.pojo.Spit;
import entity.PageResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.sql.Date;
import java.util.List;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/1/21 14:42
 * @Version: 1.0
 */
@Service
@Transactional
public class SpitService {
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Spit> findAll(){
        return spitDao.findAll();
    }
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    public void add(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        spit.setComment(0);
        spit.setPublishtime(new Date(System.currentTimeMillis()));
        spit.setThumbup(0);
        spit.setShare(0);
        spit.setVisits(0);
        spit.setState("1");
        //根据是否存在父级id判断是否为原创吐槽还是跟帖
        if(spit.getParentid()!=null && !"".equals(spit.getParentid())){
            Query query = new Query();
            Update update = new Update();
            /*添加查询条件*/
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            /*添加更新，评论数自增*/
            update.inc("comment",1);
            mongoTemplate.updateFirst(query, update, "spit");

        }
        spitDao.save(spit);
    }
    public void update(Spit spit){
        spitDao.save(spit);
    }
    public void delete(String id){
        spitDao.deleteById(id);
    }
    public Page<Spit> findByParentId(String id, int page, int size){
        Pageable pageable = PageRequest.of(page-1,size);
        return spitDao.findByParentid(id,pageable);
    }

    public void thumup(String id) {
/*        Spit spit = spitDao.findById(id).get();
        spit.setThumbup(spit.getThumbup()==null?1:spit.getThumbup()+1);
        spitDao.save(spit);*/
        Query query = new Query();
        Update update = new Update();
        /*添加查询条件*/
        query.addCriteria(Criteria.where("_id").is(id));
        /*添加更新，点赞数自增*/
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query, update,"spit");
    }
}
