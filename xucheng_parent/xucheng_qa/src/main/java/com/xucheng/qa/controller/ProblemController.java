package com.xucheng.qa.controller;
import java.util.List;
import java.util.Map;

import com.xucheng.qa.feign.BaseClient;
import com.xucheng.qa.pojo.PFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.xucheng.qa.pojo.Problem;
import com.xucheng.qa.service.ProblemService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 问题控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@Autowired
	private BaseClient baseClient;

	@Autowired
	private HttpServletRequest
			httpServletRequest;

	/*用户关注的所有标签*/
	@PostMapping("/userLabel/{userid}")
	public List<Result> getUserLabel(@PathVariable String userid){
		return problemService.labelsOfUser(userid);
	}

	/*通过redis判断关注与否*/
	@GetMapping("/getULFollow/{rediskey}")
	public Result getThumbup(@PathVariable String rediskey){

		return problemService.getULFollow(rediskey);
	}

	/*用户关注标签*/
	@PostMapping("/followLabel/{userid}/{labelid}")
	public Result userFollowLabel(@PathVariable String userid,@PathVariable String labelid){
		return problemService.userFollowLabel(userid, labelid);
	}

	/**
	 * 增加
	 * @param pFollows
	 */
	@RequestMapping(value="/PFollow",method=RequestMethod.POST)
	public Result addPFollow(@RequestBody PFollow[] pFollows  ){
		if(httpServletRequest.getAttribute("claims_user")!=null){
			problemService.adds(pFollows);
			return new Result(true,StatusCode.OK,"增加成功");
		}else {
			return new Result(false, StatusCode.ACCESSERROR, "身份验证失败");
		}
	}

	/*查询问题关联标签*/
	@PostMapping("/problemLabel/{problemid}")
	public List<Result> problemFollowLabel(@PathVariable String problemid){
		return problemService.problemFollowLabel(problemid);
	}

	/*查询问题关联标签*/
	@PostMapping("/Labelproblem/{labelid}")
	public Result LabelFollowproblem(@PathVariable String labelid){
		List<Problem> problems = problemService.LabelFollowproblem(labelid);
		return new Result(true,StatusCode.OK,"查询成功",problems);
	}


	/*通过feign调用base微服务接口*/
	@GetMapping("/lable/{labelid}")
	public Result findByLabelId(@PathVariable String labelid){
		Result result = baseClient.findById(labelid);
		return result;
	}
   /** 
   * @Description: 最新问题 
   * @Param: [labelid, page, size] 
   * @return: entity.Result 
   * @Author: yangxucheng 
   * @Date: 2019/1/18 11:06
   */

	@GetMapping("/newlist/{labelid}/{page}/{size}")
	public Result newList(@PathVariable String labelid, @PathVariable int page, @PathVariable int size){

		String header = httpServletRequest.getHeader("Authorization");//没用，可删
		System.out.print(header+"测试");
		Page<Problem> problemPage = problemService.newlist(labelid, page, size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(problemPage.getTotalElements(),problemPage.getContent()));
	}
	/** 
	* @Description: 最热问题 
	* @Param: [labelid, page, size] 
	* @return: entity.Result 
	* @Author: yangxucheng 
	* @Date: 2019/1/18 11:06
	*/ 
	@GetMapping("/hotlist/{labelid}/{page}/{size}")
	public Result hotList(@PathVariable String labelid, @PathVariable int page, @PathVariable int size){
		Page<Problem> problemPage = problemService.hotlist(labelid, page, size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(problemPage.getTotalElements(),problemPage.getContent()));
	}
	/** 
	* @Description: 等待回答的问题
	* @Param: [labelid, page, size] 
	* @return: entity.Result 
	* @Author: yangxucheng 
	* @Date: 2019/1/18 11:06
	*/ 
	@GetMapping("/waitlist/{labelid}/{page}/{size}")
	public Result waitList(@PathVariable String labelid, @PathVariable int page, @PathVariable int size){
		Page<Problem> problemPage = problemService.waitlist(labelid, page, size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(problemPage.getTotalElements(),problemPage.getContent()));
	}
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findById(id));
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
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",problemService.findSearch(searchMap));
    }



	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem  ){
		System.out.print(problem);
		if(httpServletRequest.getAttribute("claims_user")!=null){
			Problem problem1 = problemService.add(problem);
			return new Result(true,StatusCode.OK,"增加成功",problem1);
		}else {
			return new Result(false, StatusCode.ACCESSERROR, "身份验证失败");
		}
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
