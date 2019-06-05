package com.xucheng.article.service;

import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.xucheng.article.feign.UserFeignClient;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.xucheng.article.dao.ColumnDao;
import com.xucheng.article.pojo.Column;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class ColumnService {

	@Autowired
	private ColumnDao columnDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private UserFeignClient userFeignClient;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Column> findAll() {
		return columnDao.findAll();
	}


	public List<HashMap<String, Object>> findSearchByRand(String userid) {
		String[] followid={};
		List<String> idlist= new ArrayList<String>();
		/*首先判断前端是否传了用户id，如果是找到他关注的*/
		if(userid!=null && !userid.equals("")){
			followid = userFeignClient.findAllTargetUser(userid);
			System.out.println("length:"+ followid.length);
			for (String id: followid) {
				idlist.add(id);
			}
			idlist.add(userid);
		}
		idlist.add("-1");
		System.out.println(userid);
		//随机查询作者排除自己和已经关注的
		List<Column> list =  columnDao.getRandColumn(idlist);
		List<HashMap<String, Object>> listmap = new LinkedList<>();
		for (int i=0;i< list.size();i++) {
			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("id",list.get(i).getId());
			hashMap.put("name",list.get(i).getName());
			hashMap.put("summary",list.get(i).getSummary());
			hashMap.put("state",list.get(i).getState());
			hashMap.put("userid",list.get(i).getUserid());
			hashMap.put("createtime",list.get(i).getCreatetime());
			Result result = userFeignClient.findById(list.get(i).getUserid());
			hashMap.put("user", result.getData());
			listmap.add(hashMap);
		}
		return listmap;
	}
	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Column> findSearch(Map whereMap, int page, int size) {
		Specification<Column> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return columnDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Column> findSearch(Map whereMap) {
		Specification<Column> specification = createSpecification(whereMap);
		return columnDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Column findById(String id) {
		return columnDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param column
	 */
	public void add(Column column) {
		column.setId( idWorker.nextId()+"" );
		columnDao.save(column);
	}

	/**
	 * 修改
	 * @param column
	 */
	public void update(Column column) {
		columnDao.save(column);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		columnDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Column> createSpecification(Map searchMap) {

		return new Specification<Column>() {

			@Override
			public Predicate toPredicate(Root<Column> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.equal(root.get("id").as(String.class), (String)searchMap.get("id")));
                }
                // 专栏名称
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 专栏简介
                if (searchMap.get("summary")!=null && !"".equals(searchMap.get("summary"))) {
                	predicateList.add(cb.like(root.get("summary").as(String.class), "%"+(String)searchMap.get("summary")+"%"));
                }
                // 用户ID
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.equal(root.get("userid").as(String.class), (String)searchMap.get("userid")));
                }
                // 状态
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.equal(root.get("state").as(String.class), (String)searchMap.get("state")));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}


}
