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

import com.xucheng.qa.dao.ProblemDao;
import com.xucheng.qa.feign.UserFeignClient;
import com.xucheng.qa.pojo.Problem;
import com.xucheng.qa.pojo.User;
import entity.Result;
import entity.StatusCode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import com.xucheng.qa.dao.ReplyDao;
import com.xucheng.qa.pojo.Reply;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class ReplyService {

	@Autowired
	private ProblemDao problemDao;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private UserFeignClient userFeignClient;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private ReplyDao replyDao;
	
	@Autowired
	private IdWorker idWorker;

	public Result thumbup(String replyId, String userid, int thumbup) {
		if(httpServletRequest.getAttribute("claims_user")!=null){
			if(redisTemplate.opsForValue().get("reply_"+replyId+"_"+userid)!=null){
				return new Result(false, StatusCode.ERROR, "不能重复点赞");
			}else{
				redisTemplate.opsForValue().set("reply_"+replyId+"_"+userid,"1");
				replyDao.thumbup(replyId, thumbup);
				Map<String, String> map = new HashMap<>();
				Result result = userFeignClient.findById(userid);
				/*这个是点赞的用户,object被默认转为linkedHashMap*/
				LinkedHashMap linkedHashMap = (LinkedHashMap) result.getData();
				Reply reply = replyDao.findById(replyId).get();
				/*这条消息发给作者*/
				map.put("content", reply.getUserid()+";"+linkedHashMap.get("nickname")+";点赞了你在的回答;"+reply.getContent());
				/*向消息队列发送一条消息*/
				rabbitTemplate.convertAndSend("info", map);
				return new Result(true, StatusCode.OK, "点赞成功");
			}
		}else {
			return new Result(false, StatusCode.ACCESSERROR, "身份验证失败");
		}
	}

	public Result getThumbup(String rediskey) {
		if(redisTemplate.opsForValue().get(rediskey)!=null){
			return new Result(true, StatusCode.OK, "已点赞");
		}else{
			return new Result(false, StatusCode.ERROR, "未点赞");
		}
	}

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Reply> findAll() {
		return replyDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Reply> findSearch(Map whereMap, int page, int size) {
		Specification<Reply> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return replyDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Reply> findSearch(Map whereMap) {
		Specification<Reply> specification = createSpecification(whereMap);
		return replyDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Reply findById(String id) {
		return replyDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param reply
	 */
	public Reply add(Reply reply) {
		reply.setId( idWorker.nextId()+"" );
		System.out.print(reply.getContent());
		reply.setCreatetime(new Date(System.currentTimeMillis()));
		replyDao.save(reply);
		Map<String, String> map = new HashMap<>();
		Problem problem = problemDao.findById(reply.getProblemid()).get();
		/*要发给提问者*/
		map.put("content", problem.getUserid()+";"+reply.getNickname()+";回答了你的问题;"+problem.getContent());
		/*向消息队列发送一条消息*/
		rabbitTemplate.convertAndSend("info", map);
		return reply;
	}

	/**
	 * 修改
	 * @param reply
	 */
	public void update(Reply reply) {
		replyDao.save(reply);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		replyDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Reply> createSpecification(Map searchMap) {

		return new Specification<Reply>() {

			@Override
			public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 编号
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.equal(root.get("id").as(String.class), (String)searchMap.get("id")));
                }
                // 问题ID
                if (searchMap.get("problemid")!=null && !"".equals(searchMap.get("problemid"))) {
                	predicateList.add(cb.equal(root.get("problemid").as(String.class), (String)searchMap.get("problemid")));
                }
                // 回答内容
                if (searchMap.get("content")!=null && !"".equals(searchMap.get("content"))) {
                	predicateList.add(cb.like(root.get("content").as(String.class), "%"+(String)searchMap.get("content")+"%"));
                }
                // 回答人ID
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.equal(root.get("userid").as(String.class), (String)searchMap.get("userid")));
                }
                // 回答人昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
