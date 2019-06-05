package com.xucheng.qa.service;

import java.sql.Date;
import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.servlet.http.HttpServletRequest;

import com.xucheng.qa.dao.PFollowDao;
import com.xucheng.qa.dao.UFollowDao;
import com.xucheng.qa.feign.BaseClient;
import com.xucheng.qa.pojo.PFollow;
import com.xucheng.qa.pojo.UFollow;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import com.xucheng.qa.dao.ProblemDao;
import com.xucheng.qa.pojo.Problem;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Transactional
@Service
public class ProblemService {

	@Autowired
	private BaseClient baseClient;

	@Autowired
	private ProblemDao problemDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private UFollowDao uFollowDao;

	@Autowired
	private PFollowDao pFollowDao;

	/**
	* @Description: 获得用户关注的所有标签
	* @Param: [uid]
	* @return: java.util.List<entity.Result>
	* @Author: yangxucheng
	* @Date: 2019/4/8 16:42
	*/
	public List<Result> labelsOfUser(String uid){
		List<UFollow> uFollows= uFollowDao.findAllByUid(uid);
		List<Result> results = new LinkedList<>();
		for (UFollow uFollow: uFollows) {
			Result result = baseClient.findById(uFollow.getlid());
			results.add(result);
		}
		return results;
	}
	/*查询某个标签下的所有问题*/
	public List<Problem> LabelFollowproblem(String labelid){
		List<PFollow> pFollows= pFollowDao.findAllByLabelid(labelid);
		List<Problem> problems = new LinkedList<>();
		for (PFollow pFollow: pFollows) {
			Problem problem = problemDao.findById(pFollow.getProblemid()).get();
			problems.add(problem);
		}
		return problems;
	}
	/*获得某个问题所对应的标签*/
	public List<Result> problemFollowLabel(String probelmid){
		List<PFollow> pFollows= pFollowDao.findAllByProblemid(probelmid);
		List<Result> results = new LinkedList<>();
		for (PFollow pFollow: pFollows) {
			Result result = baseClient.findById(pFollow.getLabelid());
			results.add(result);
		}
		return results;
	}
	/*通过redis判断关注与否*/
	public Result getULFollow(String rediskey) {
		if(redisTemplate.opsForValue().get(rediskey)!=null){
			return new Result(true, StatusCode.OK, "已关注");
		}else{
			return new Result(false, StatusCode.ERROR, "未关注");
		}
	}

	/*用户关注标签或者取消*/
	public Result userFollowLabel(String userid, String labelid) {
		UFollow uFollow = new UFollow(idWorker.nextId()+"",userid,labelid);
		if(httpServletRequest.getAttribute("claims_user")!=null){
			if(redisTemplate.opsForValue().get("ul_"+userid+"_"+labelid)!=null){
				redisTemplate.delete("ul_"+userid+"_"+labelid);
				uFollowDao.deleteUfollow(userid, labelid);
				baseClient.updateFans(labelid, -1);
				return new Result(false, StatusCode.ERROR, "已取消关注");
			}else{
				redisTemplate.opsForValue().set("ul_"+userid+"_"+labelid,"1");
				uFollowDao.save(uFollow);
				baseClient.updateFans(labelid, 1);
				return new Result(true, StatusCode.OK, "已关注");
			}
		}else {
			return new Result(false, StatusCode.ACCESSERROR, "身份验证失败");
		}
	}
	public Page<Problem> newlist(String labelId, int page, int size){
		PageRequest pageRequest = PageRequest.of(page-1,size);
		List<PFollow> list = new LinkedList<>();
		PFollow pFollow = new PFollow("-1","-1","-1");
		if(!labelId.equals("-1")){
			list = pFollowDao.findAllByLabelid(labelId);
			list.add(pFollow);
		}
		Specification<Problem> specification = createSpecificationNew(list);
		return problemDao.findAll(specification, pageRequest);

	}

	public Page<Problem> hotlist(String labelId, int page, int size){
		PageRequest pageRequest = PageRequest.of(page-1,size);
		List<PFollow> list = new LinkedList<>();
		PFollow pFollow = new PFollow("-1","-1","-1");
		if(!labelId.equals("-1")){
			list = pFollowDao.findAllByLabelid(labelId);
			list.add(pFollow);
		}
		Specification<Problem> specification = createSpecificationHot(list);
		return problemDao.findAll(specification, pageRequest);

	}

	public Page<Problem> waitlist(String labelId, int page, int size){
		PageRequest pageRequest = PageRequest.of(page-1,size);
		List<PFollow> list = new LinkedList<>();
		PFollow pFollow = new PFollow("-1","-1","-1");
		if(!labelId.equals("-1")){
			list = pFollowDao.findAllByLabelid(labelId);
			/*防止该标签下没有问题时将查询全部的bug*/
			list.add(pFollow);
		}
		Specification<Problem> specification = createSpecificationWait(list);
		return problemDao.findAll(specification, pageRequest);

	}
	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Problem> findAll() {
		return problemDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Problem> findSearch(Map whereMap, int page, int size) {
		Specification<Problem> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return problemDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Problem> findSearch(Map whereMap) {
		Specification<Problem> specification = createSpecification(whereMap);
		return problemDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Problem findById(String id) {
		return problemDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param problem
	 */
	public Problem add(Problem problem) {

		problem.setId( idWorker.nextId()+"" );
		problem.setCreatetime(new Date(System.currentTimeMillis()));
		long reply = 0;
		problem.setReply(reply);
		problem.setSolve("0");
		problem.setThumbup(reply);
		problem.setUpdatetime(new Date(System.currentTimeMillis()));
		problem.setVisits(reply);
		System.out.print(problem);
		problemDao.save(problem);
		return problem;
	}

	/**
	 * 修改
	 * @param problem
	 */
	public void update(Problem problem) {
		problemDao.save(problem);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		problemDao.deleteById(id);
	}


	private Specification<Problem> createSpecificationNew(List<PFollow> list) {
		return new Specification<Problem>() {
			@Override
			public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicateList = new ArrayList<Predicate>();
				if(list.size()>0){
					CriteriaBuilder.In in = cb.in(root.get("id"));
					String[] problemid ={};
					for(PFollow pFollow :list){
						in.value(pFollow.getProblemid());
					}
					predicateList.add(in);
				}
				query.orderBy(cb.desc(root.get("replytime")));
				Predicate[] predicates = new Predicate[predicateList.size()];
				query.where(cb.and(predicateList.toArray(predicates)));
				return query.getRestriction();
			}
		};
	}
	private Specification<Problem> createSpecificationHot(List<PFollow> list) {
		return new Specification<Problem>() {
			@Override
			public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				if(list.size()>0){
					CriteriaBuilder.In in = cb.in(root.get("id"));
					String[] problemid ={};
					for(PFollow pFollow :list){
						in.value(pFollow.getProblemid());
					}
					predicateList.add(in);
				}
				query.orderBy(cb.desc(root.get("reply")));
				Predicate[] predicates = new Predicate[predicateList.size()];
				query.where(cb.and(predicateList.toArray(predicates)));
				return query.getRestriction();
			}
		};
	}
	private Specification<Problem> createSpecificationWait(List<PFollow> list) {
		return new Specification<Problem>() {
			@Override
			public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				if(list.size()>0){
					CriteriaBuilder.In in = cb.in(root.get("id"));
					String[] problemid ={};
					for(PFollow pFollow :list){
						in.value(pFollow.getProblemid());
					}
					predicateList.add(in);
				}
				predicateList.add(cb.equal(root.get("reply"),"0"));
				query.orderBy(cb.desc(root.get("createtime")));
				Predicate[] predicates = new Predicate[predicateList.size()];
				query.where(cb.and(predicateList.toArray(predicates)));
				return query.getRestriction();
			}
		};
	}
	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Problem> createSpecification(Map searchMap) {

		return new Specification<Problem>() {

			@Override
			public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.equal(root.get("id").as(String.class), searchMap.get("id")));
                }
                // 标题
                if (searchMap.get("title")!=null && !"".equals(searchMap.get("title"))) {
                	predicateList.add(cb.like(root.get("title").as(String.class), "%"+(String)searchMap.get("title")+"%"));
                }
                // 内容
                if (searchMap.get("content")!=null && !"".equals(searchMap.get("content"))) {
                	predicateList.add(cb.like(root.get("content").as(String.class), "%"+(String)searchMap.get("content")+"%"));
                }
                // 用户ID
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.equal(root.get("userid").as(String.class), searchMap.get("userid")));
                }
                // 昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // 是否解决
                if (searchMap.get("solve")!=null && !"".equals(searchMap.get("solve"))) {
                	predicateList.add(cb.like(root.get("solve").as(String.class), "%"+(String)searchMap.get("solve")+"%"));
                }
                // 回复人昵称
                if (searchMap.get("replyname")!=null && !"".equals(searchMap.get("replyname"))) {
                	predicateList.add(cb.like(root.get("replyname").as(String.class), "%"+(String)searchMap.get("replyname")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}


	public void adds(PFollow[] pFollows) {
		IdWorker idWorker = new IdWorker();
		for (PFollow pFollow: pFollows) {
			pFollow.setId(idWorker.nextId()+"");
			pFollowDao.save(pFollow);
		}
	}
}
