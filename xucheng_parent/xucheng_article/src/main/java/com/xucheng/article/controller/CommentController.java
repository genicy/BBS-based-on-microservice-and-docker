package com.xucheng.article.controller;
import java.util.Map;

import com.xucheng.article.feign.UserFeignClient;
import com.xucheng.article.pojo.Comment;
import com.xucheng.article.service.CommentService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private UserFeignClient userFeignClient;

	@Autowired
	private CommentService commentService;


	@GetMapping("/getThumbup/{rediskey}")
	public Result getThumbup(@PathVariable String rediskey){

		return commentService.getThumbup(rediskey);
	}
	/*点赞*/
	@PutMapping("/thumbup/{commentId}/{userid}")
	public Result thumbup(@PathVariable String commentId, @PathVariable String userid){
		return commentService.thumbup(commentId, userid);
	}
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",commentService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",commentService.findById(id));
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
		Page<Comment> pageList = commentService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Comment>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",commentService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param comment
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Comment comment ){
		commentService.add(comment);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param comment
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Comment comment, @PathVariable String id ){
		comment.setId(id);
		commentService.update(comment);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		commentService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
