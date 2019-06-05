package com.xucheng.spit.controller;

import com.xucheng.spit.pojo.Spit;
import com.xucheng.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/1/21 14:53
 * @Version: 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {
    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }
    @GetMapping(value="/{spitId}")
    public Result findById(@PathVariable String spitId){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findById(spitId));
    }
    @PostMapping
    public Result add(@RequestBody Spit Spit  ){
        spitService.add(Spit);
        return new Result(true,StatusCode.OK,"增加成功");
    }

    @PutMapping("/{spitId}")
    public Result update(@PathVariable String spitId, @RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true, StatusCode.OK,"查询成功");
    }
    @DeleteMapping(value="/{spitId}")
    public Result delete(@PathVariable String spitId ){
        spitService.delete(spitId);
        return new Result(true,StatusCode.OK,"删除成功");
    }
    /** 
    * @Description: 根据父级id分页
    * @Param: [parentid, page, size] 
    * @return: entity.Result 
    * @Author: yangxucheng 
    * @Date: 2019/1/21 15:36
    */ 
    @GetMapping("/comment/{parentid}/{page}/{size}")
    public Result findAll(@PathVariable String parentid,@PathVariable int page, @PathVariable int size){
        Page<Spit> pageResult = spitService.findByParentId(parentid,page,size);
        return new Result(true, StatusCode.OK,"查询成功",new PageResult<Spit>(pageResult.getTotalElements(),pageResult.getContent()));
    }


    /**
    * @Description: 点赞
    * @Param: [spitId]
    * @return: entity.Result
    * @Author: yangxucheng
    * @Date: 2019/1/21 16:17
    */
    @PutMapping("/thumbup/{spitId}")
    public Result thumbup(@PathVariable String spitId){
        String userId = "111";
        /*
        * 通过redis进行判断该用户在该吐槽下是否已经点赞
        * */
        if(redisTemplate.opsForValue().get("thumup_"+userId+spitId)!=null){
            return new Result(false, StatusCode.ERROR,"不能重复点赞");

        }
        redisTemplate.opsForValue().set("thumup_"+userId+spitId,1);
        spitService.thumup(spitId);
        return new Result(true, StatusCode.OK,"点赞成功");
    }
}
