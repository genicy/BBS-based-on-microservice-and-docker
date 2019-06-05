package com.xucheng.article.service;

import java.sql.Date;
import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.xucheng.article.dao.ArticleDao;
import com.xucheng.article.dao.CommentDao;
import com.xucheng.article.feign.UserFeignClient;
import com.xucheng.article.pojo.Article;
import com.xucheng.article.pojo.Comment;
import com.xucheng.article.pojo.User;
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


/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class CommentService {

	@Autowired
	private UserFeignClient userFeignClient;

	@Autowired
	private ArticleDao articleDao;

	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Comment> findAll() {
		return commentDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Comment> findSearch(Map whereMap, int page, int size) {
		Specification<Comment> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return commentDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Comment> findSearch(Map whereMap) {
		Specification<Comment> specification = createSpecification(whereMap);
		return commentDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Comment findById(String id) {
		return commentDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param comment
	 */
	public void add(Comment comment) {
		comment.setId( idWorker.nextId()+"" );
		Map<String, String> map = new HashMap<>();
		Result result = userFeignClient.findById(comment.getUserid());
		/*这个user是评论的用户*/
       /*object默认转为linkedHashMap*/
		LinkedHashMap linkedHashMap = (LinkedHashMap) result.getData();
		Article article = articleDao.findById(comment.getArticleid()).get();
		/*这条消息发给作者*/
		map.put("content", article.getUserid()+";"+linkedHashMap.get("nickname")+";评论了你的文章;"+article.getTitle());
		/*向消息队列发送一条消息*/
		rabbitTemplate.convertAndSend("info", map);
		comment.setCreatetime(new Date(System.currentTimeMillis()));
		commentDao.save(comment);
	}

	/**
	 * 修改
	 * @param comment
	 */
	public void update(Comment comment) {
		commentDao.save(comment);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		commentDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Comment> createSpecification(Map searchMap) {

		return new Specification<Comment>() {

			@Override
			public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 
                if (searchMap.get("articleid")!=null && !"".equals(searchMap.get("articleid"))) {
                	predicateList.add(cb.equal(root.get("articleid").as(String.class), (String)searchMap.get("articleid")));
                }
                // 
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.equal(root.get("userid").as(String.class), (String)searchMap.get("userid")));
                }
                // 
                if (searchMap.get("comment")!=null && !"".equals(searchMap.get("comment"))) {
                	predicateList.add(cb.like(root.get("comment").as(String.class), "%"+(String)searchMap.get("comment")+"%"));
                }
                // 向谁回复
                if (searchMap.get("replyid")!=null && !"".equals(searchMap.get("replyid"))) {
                	predicateList.add(cb.equal(root.get("replyid").as(String.class), (String)searchMap.get("replyid")));
                }
                // 层主id（会包含多级评论）
                if (searchMap.get("taskid")!=null && !"".equals(searchMap.get("taskid"))) {
                	predicateList.add(cb.equal(root.get("taskid").as(String.class), (String)searchMap.get("taskid")));
                }
				if(searchMap.get("type")!=null && !"2".equals(searchMap.get("type"))){
					query.orderBy(cb.desc(root.get("thumbup")));
				}
				else if(searchMap.get("type")!=null && !"1".equals(searchMap.get("type"))){
					query.orderBy(cb.desc(root.get("createtime")));
				}
				Predicate[] predicates = new Predicate[predicateList.size()];
				query.where(cb.and( predicateList.toArray(predicates)));
				return query.getRestriction();

			}
		};

	}

	public Result thumbup(String commentId, String userid) {
		if(httpServletRequest.getAttribute("claims_user")!=null){
			if(redisTemplate.opsForValue().get("comment_"+commentId+"_"+userid)!=null){
				return new Result(false, StatusCode.ERROR, "不能重复点赞");
			}else{
				redisTemplate.opsForValue().set("comment_"+commentId+"_"+userid,"1");
				commentDao.thumbup(commentId);
				Map<String, String> map = new HashMap<>();
				Result result = userFeignClient.findById(userid);
				/*这个是点赞的用户,object被默认转为linkedHashMap*/
				LinkedHashMap linkedHashMap = (LinkedHashMap) result.getData();
				Comment comment = commentDao.findById(commentId).get();
				Article article = articleDao.findById(comment.getArticleid()).get();
				/*要发给原作者*/
				map.put("content", article.getUserid()+";"+linkedHashMap.get("nickname")+";点赞了你的评论;"+comment.getComment());
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
}
