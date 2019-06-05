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

import com.xucheng.article.feign.UserFeignClient;
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
import org.springframework.web.client.RestTemplate;
import util.IdWorker;

import com.xucheng.article.dao.ArticleDao;
import com.xucheng.article.pojo.Article;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class ArticleService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private UserFeignClient userFeignClient;

	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private HttpServletRequest httpServletRequest;

	public Result thumbup(String articleId, String userid){

		if(httpServletRequest.getAttribute("claims_user")!=null){
			if(redisTemplate.opsForValue().get("article_"+articleId+"_"+userid)!=null){
				return new Result(false, StatusCode.ERROR, "不能重复点赞");
			}else{
				/*redis记录此次点赞*/
				redisTemplate.opsForValue().set("article_"+articleId+"_"+userid,"1");
				/*修改数据库*/
				articleDao.thumbup(articleId);
				Map<String, String> map = new HashMap<>();
				Result result = userFeignClient.findById(userid);
				/*这个是点赞的用户*/
				/*这个是点赞的用户,object被默认转为linkedHashMap*/
				LinkedHashMap linkedHashMap = (LinkedHashMap) result.getData();
				Article article = articleDao.findById(articleId).get();
				/*要发给原作者*/
				map.put("content", article.getUserid()+";"+linkedHashMap.get("nickname")+";点赞了你的文章;"+article.getTitle());
				/*向消息队列发送一条消息*/
				rabbitTemplate.convertAndSend("info", map);
				return new Result(true, StatusCode.ERROR, "点赞成功");
			}
		}else {
			return new Result(false, StatusCode.ACCESSERROR, "身份验证失败");
		}


	}
	public void examine(String articleId){
		articleDao.examine(articleId);
	}
	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Article> findAll() {
		return articleDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Article> findSearch(Map whereMap, int page, int size, int byThumbup) {

		Specification<Article> specification = createSpecification(whereMap,byThumbup);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return articleDao.findAll(specification, pageRequest);
	}
	
	/** 
	* @Description: 分页条件查询 最火
	* @Param: [whereMap, page, size] 
	* @return: org.springframework.data.domain.Page<com.xucheng.article.pojo.Article> 
	* @Author: yangxucheng 
	* @Date: 2019/3/15 15:53
	*/ 
/*	public Page<Article> findByThumbupSearch(Map whereMap, int page, int size) {
		Specification<Article> specification = createSpecification(whereMap,true);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return articleDao.findAll(specification, pageRequest);
	}*/

	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Article> findSearch(Map whereMap) {
		Specification<Article> specification = createSpecification(whereMap);
		return articleDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */

	public Article findById(String id) {
		Article article = (Article) redisTemplate.opsForValue().get("article_"+id);
		if(article == null){
			article = articleDao.findById(id).get();
			redisTemplate.opsForValue().set("article_"+id,article);
		}
		return article;
	}

	/**
	 * 增加
	 * @param article
	 */
	public void add(Article article) {
		article.setId( idWorker.nextId()+"" );
		article.setCreatetime(new Date(System.currentTimeMillis()));
		articleDao.save(article);
	}

	/**
	 * 修改
	 * @param article
	 */
	public void update(Article article) {
		redisTemplate.delete("article_"+article.getId());
		articleDao.save(article);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		redisTemplate.delete("article_"+id);
		articleDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Article> createSpecification(Map searchMap) {
		return new Specification<Article>() {
			@Override
			public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 专栏ID
                if (searchMap.get("columnid")!=null && !"".equals(searchMap.get("columnid"))) {
                	predicateList.add(cb.like(root.get("columnid").as(String.class), "%"+(String)searchMap.get("columnid")+"%"));
                }
                // 用户ID
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.like(root.get("userid").as(String.class), "%"+(String)searchMap.get("userid")+"%"));
                }
                // 标题
                if (searchMap.get("title")!=null && !"".equals(searchMap.get("title"))) {
                	predicateList.add(cb.like(root.get("title").as(String.class), "%"+(String)searchMap.get("title")+"%"));
                }
                // 文章正文
                if (searchMap.get("content")!=null && !"".equals(searchMap.get("content"))) {
                	predicateList.add(cb.like(root.get("content").as(String.class), "%"+(String)searchMap.get("content")+"%"));
                }
                // 文章封面
                if (searchMap.get("image")!=null && !"".equals(searchMap.get("image"))) {
                	predicateList.add(cb.like(root.get("image").as(String.class), "%"+(String)searchMap.get("image")+"%"));
                }
                // 是否公开
                if (searchMap.get("ispublic")!=null && !"".equals(searchMap.get("ispublic"))) {
                	predicateList.add(cb.like(root.get("ispublic").as(String.class), "%"+(String)searchMap.get("ispublic")+"%"));
                }
                // 是否置顶
                if (searchMap.get("istop")!=null && !"".equals(searchMap.get("istop"))) {
                	predicateList.add(cb.like(root.get("istop").as(String.class), "%"+(String)searchMap.get("istop")+"%"));
                }
                // 审核状态
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
                // 所属频道
                if (searchMap.get("channelid")!=null && !"".equals(searchMap.get("channelid"))) {
                	predicateList.add(cb.like(root.get("channelid").as(String.class), "%"+(String)searchMap.get("channelid")+"%"));
                }
                // URL
                if (searchMap.get("url")!=null && !"".equals(searchMap.get("url"))) {
                	predicateList.add(cb.like(root.get("url").as(String.class), "%"+(String)searchMap.get("url")+"%"));
                }
                // 类型
                if (searchMap.get("type")!=null && !"".equals(searchMap.get("type"))) {
                	predicateList.add(cb.like(root.get("type").as(String.class), "%"+(String)searchMap.get("type")+"%"));
                }
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	/**
	* @Description:
	* @Param: [searchMap, thumbup]  查询条件，最新/热门/关注
	* @return: org.springframework.data.jpa.domain.Specification<com.xucheng.article.pojo.Article>
	* @Author: yangxucheng
	* @Date: 2019/3/18 9:30
	*/
	private Specification<Article> createSpecification(Map searchMap, int thumbup) {

		return new Specification<Article>() {

			@Override
			public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				// ID
				if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
					predicateList.add(cb.equal(root.get("id").as(String.class), searchMap.get("id")));
				}
				// 专栏ID
				if (searchMap.get("columnid")!=null && !"".equals(searchMap.get("columnid"))) {
					predicateList.add(cb.equal(root.get("columnid").as(String.class), searchMap.get("columnid")));
				}
				// 用户ID
				if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
					predicateList.add(cb.equal(root.get("userid").as(String.class), (String)searchMap.get("userid")));
				}
				// 标题
				if (searchMap.get("title")!=null && !"".equals(searchMap.get("title"))) {
					predicateList.add(cb.like(root.get("title").as(String.class), "%"+(String)searchMap.get("title")+"%"));
				}
				// 文章正文
				if (searchMap.get("content")!=null && !"".equals(searchMap.get("content"))) {
					predicateList.add(cb.like(root.get("content").as(String.class), "%"+(String)searchMap.get("content")+"%"));
				}
				// 文章封面
				if (searchMap.get("image")!=null && !"".equals(searchMap.get("image"))) {
					predicateList.add(cb.like(root.get("image").as(String.class), "%"+(String)searchMap.get("image")+"%"));
				}
				// 是否公开
				if (searchMap.get("ispublic")!=null && !"".equals(searchMap.get("ispublic"))) {
					predicateList.add(cb.like(root.get("ispublic").as(String.class), "%"+(String)searchMap.get("ispublic")+"%"));
				}
				// 是否置顶
				if (searchMap.get("istop")!=null && !"".equals(searchMap.get("istop"))) {
					predicateList.add(cb.like(root.get("istop").as(String.class), "%"+(String)searchMap.get("istop")+"%"));
				}
				// 审核状态
				if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
					predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
				}
				// 所属频道
				if (searchMap.get("channelid")!=null && !"".equals(searchMap.get("channelid"))) {
					predicateList.add(cb.equal(root.get("channelid").as(String.class), searchMap.get("channelid")));
				}
				// URL
				if (searchMap.get("url")!=null && !"".equals(searchMap.get("url"))) {
					predicateList.add(cb.like(root.get("url").as(String.class), "%"+(String)searchMap.get("url")+"%"));
				}
				// 类型
				if (searchMap.get("type")!=null && !"".equals(searchMap.get("type"))) {
					predicateList.add(cb.like(root.get("type").as(String.class), "%"+(String)searchMap.get("type")+"%"));
				}


				/*点击热门文章的情况*/
				if(thumbup==1){
					query.orderBy(cb.desc(root.get("thumbup")));
				}
				/*点击关注文章的情况*/
				if(thumbup==2){
					CriteriaBuilder.In<String> in= cb.in(root.get("userid"));
					String[] list = userFeignClient.findAllTargetUser((String)searchMap.get("user_id"));
					if(list!=null && list.length>0){
						for (String targuser: list) {
							System.out.println(targuser);
							in.value(targuser);
						}
					}
					predicateList.add(in);
				}
				Predicate[] p = new Predicate[predicateList.size()];
				query.where(cb.and((predicateList.toArray(p))));
				return query.getRestriction();
			}
		};

	}

}
