package com.xucheng.user.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.servlet.http.HttpServletRequest;

import com.xucheng.user.dao.FollowDao;
import com.xucheng.user.pojo.Follow;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import com.xucheng.user.dao.UserDao;
import com.xucheng.user.pojo.User;
import util.JwtUtil;
import java.sql.Date;
/**
 * 服务层
 * 有修改更新删除操作需要添加@Transactional
 * @author Administrator
 *
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private FollowDao followDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<User> findAll() {
        return userDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<User> findSearch(Map whereMap, int page, int size) {
        Specification<User> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<User> findSearch(Map whereMap) {
        Specification<User> specification = createSpecification(whereMap);
        return userDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public User findById(String id) {
        System.out.println("用户id"+id);
        return userDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param user
     */
    public void add(User user) {
        user.setId(idWorker.nextId() + "");
        user.setPassword(encoder.encode(user.getPassword()));//密码加密
        user.setFanscount(0);
        user.setFollowcount(0);//关注数
        user.setFanscount(0);//粉丝数
        user.setOnline(0L);//在线时长
        user.setRegdate(new Date(System.currentTimeMillis()));//注册日期
        user.setUpdatedate(new Date(System.currentTimeMillis()));//更新日期
        user.setLastdate(new Date(System.currentTimeMillis()));//最后登陆

        userDao.save(user);
    }

    /**
     * 修改
     *
     * @param user
     */
    public void update(User user) {
        userDao.save(user);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        /*先从拦截器中判断该请求是否来自管理员角色*/
        String role = (String)request.getAttribute("claims_admin");
        if(role==null||role.equals("")){
            throw  new RuntimeException("权限不足");
        }
        userDao.deleteById(id);

    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecification(Map searchMap) {

        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 手机号码
                if (searchMap.get("mobile") != null && !"".equals(searchMap.get("mobile"))) {
                    predicateList.add(cb.like(root.get("mobile").as(String.class), "%" + (String) searchMap.get("mobile") + "%"));
                }
                // 登陆名称
                if (searchMap.get("loginname") != null && !"".equals(searchMap.get("loginname"))) {
                    predicateList.add(cb.like(root.get("loginname").as(String.class), "%" + (String) searchMap.get("loginname") + "%"));
                }
                // 密码
                if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                    predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
                }
                // 昵称
                if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                    predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
                }
                // 性别
                if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
                    predicateList.add(cb.like(root.get("sex").as(String.class), "%" + (String) searchMap.get("sex") + "%"));
                }
                // 头像
                if (searchMap.get("avatar") != null && !"".equals(searchMap.get("avatar"))) {
                    predicateList.add(cb.like(root.get("avatar").as(String.class), "%" + (String) searchMap.get("avatar") + "%"));
                }
                // E-Mail
                if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
                    predicateList.add(cb.like(root.get("email").as(String.class), "%" + (String) searchMap.get("email") + "%"));
                }
                // 兴趣
                if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest"))) {
                    predicateList.add(cb.like(root.get("interest").as(String.class), "%" + (String) searchMap.get("interest") + "%"));
                }
                // 个性
                if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality"))) {
                    predicateList.add(cb.like(root.get("personality").as(String.class), "%" + (String) searchMap.get("personality") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

    public void sendSms(String mobile) {
        String checkcode = RandomStringUtils.randomNumeric(6);
        /*缓存中保存一份验证码*/
        redisTemplate.opsForValue().set("checkcode_" + mobile, checkcode, 6, TimeUnit.HOURS);
        /*给用户发一份交给rabbitmq*/
        Map<String, String> map = new HashMap<String, String>();
        map.put("checkcode", checkcode);
        map.put("mobile", mobile);
        //暂时注掉，要钱的
/*        rabbitTemplate.convertAndSend("sms", map);*/
        logger.info("" + checkcode);
    }

    public User login(User user) {
        User userlogin = userDao.findByMobile(user.getMobile());
        User userlogin1 = userDao.findByLoginname(user.getLoginname());
        if (userlogin != null && encoder.matches(user.getPassword(), userlogin.getPassword())) {
            return userlogin;
        }
        if (userlogin1 != null && encoder.matches(user.getPassword(), userlogin1.getPassword())) {
            return userlogin1;
        }
        return null;
    }

    public void updateFollowCount(String userid, int x) {
        userDao.updateFollowCount(x,userid);
    }

    public void updateFansCount(String friendid, int x) {
        userDao.updateFansCount(x,friendid);
    }

    public String[] findAllTargetUser(String userid) {
        List<Follow> list = (List<Follow>) followDao.findTargetUser(userid);
        /*将查询出来的对象数组只取关注id作为String
        * */
        /*防止没有关注用户*/
        Follow follow = new Follow("-1","-1","-1");
        list.add(follow);
        String[] list1 = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            list1[i] = list.get(i).getTargetuser();
        }
        return  list1;
    }
}
