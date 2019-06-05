package com.xucheng.base.service;

import com.xucheng.base.dao.BaseDao;
import com.xucheng.base.pojo.Label;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/1/16 19:58
 * @Version: 1.0
 */
@Service
@Transactional
public class BaseService {
    @Autowired
    BaseDao baseDao;
    @Autowired
    IdWorker idWorker;

    public void updateFans(String id, int follow) {
        Label label = baseDao.getOne(id);
        System.out.println(label==null);
        label.setFans(label.getFans()+follow);
        baseDao.save(label);
    }

    public List<String> groupByType() {
        return baseDao.findGroupByType();
    }
    public List<Label> findAll(){
        return baseDao.findAll();
    }
    public Label findById(String id){
        return baseDao.findById(id).get();
    }
    public void insert(Label label){
        label.setId(idWorker.nextId()+"");
         baseDao.save(label);
    }
    public void update(Label label){
        baseDao.save(label);
    }
    public void delete(String id){
        baseDao.deleteById(id);
    }


    public List<Label> findBySearch(Label label) {
        return baseDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> lists = new LinkedList<Predicate>();
                if(label.getLabelname()!=null&&!label.getLabelname().equals("")){
                    Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%");
                    lists.add(predicate);
                }
                if(label.getState()!=null&&!label.getState().equals("")){
                    Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class),label.getState()+"");
                    lists.add(predicate);
                }
                if(label.getType()!=null&&!label.getType().equals("")){
                    Predicate predicate = criteriaBuilder.equal(root.get("type").as(String.class),label.getType());
                    lists.add(predicate);
                }
                Predicate predicate[] = new Predicate[lists.size()];
                lists.toArray(predicate);
                return criteriaBuilder.and(predicate);
            }
        });
    }

    public Page<Label> pageQuery(Label label, int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        return baseDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> lists = new LinkedList<Predicate>();
                if(label.getLabelname()!=null&&!label.getLabelname().equals("")){
                    Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%");
                    lists.add(predicate);
                }
                if(label.getState()!=null&&!label.getState().equals("")){
                    Predicate predicate = criteriaBuilder.like(root.get("state").as(String.class),label.getState()+"");
                    lists.add(predicate);
                }

                Predicate predicate[] = new Predicate[lists.size()];
                lists.toArray(predicate);
                return criteriaBuilder.and(predicate);
            }
        },pageable);
    }



}
