package com.xucheng.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xucheng.user.pojo.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import com.xucheng.user.pojo.User;
import com.xucheng.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private JwtUtil jwtUtil;

	/*更新用户自己的关注数与好友粉丝数
	* 供内部调用
	* */
	@PutMapping("/{userid}/{friendid}/{x}")
    public void updateFollowCountAndFansCount(@PathVariable String userid,@PathVariable String friendid,@PathVariable int x){
	    userService.updateFollowCount(userid,x);
	    userService.updateFansCount(friendid,x);
    }

    /**
    * @Description: 寻找所有自己关注的专栏用户id，一般查询关注文章使用
    * @Param: [userid]
    * @return: 返回所有关注专栏作者的id
    * @Author: yangxucheng
    * @Date: 2019/3/16 16:06
    */
	@PostMapping("/findAllTargetUser")
	public String[] findAllTargetUser(@RequestBody String userid){
		String[] list= userService.findAllTargetUser(userid);

		return list;
	}

    /*用户登录*/
    @PostMapping("/login")
    public Result login(@RequestBody User user){
		User userlogin = userService.login(user);

        if(userlogin==null){
            return new Result(false,StatusCode.ERROR,"用户 "+userlogin.getLoginname()+"登录失败");
        }
        String token = jwtUtil.createJWT(userlogin.getId(),userlogin.getLoginname(),"user");
        Map<String ,Object> map = new HashMap<>();
        map.put("role","user");
        map.put("token",token);
		map.put("user",userlogin);
        return new Result(true,StatusCode.OK,"用户 "+userlogin.getLoginname()+"登录成功",map);
    }

	/*发送短信验证码*/
	@PostMapping("/sendsms/{mobile}")
	public Result sendSms(@PathVariable String mobile){
		userService.sendSms(mobile);
		return new Result(true,StatusCode.OK,"发送成功");
	}

	@PostMapping("/register/{code}")
	public Result regist(@PathVariable String code,@RequestBody User user){
		String mobile = user.getMobile();
		String checkcode = (String)redisTemplate.opsForValue().get("checkcode_"+mobile);
		if(checkcode.isEmpty()){
			return new Result(true,StatusCode.OK,"验证码为空");
		}
		else if(!checkcode.equals(code)){
			return new Result(true,StatusCode.OK,"验证码不一致");
		}
		userService.add(user);
		return new Result(true,StatusCode.OK,"注册成功");

	}
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.GET)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
