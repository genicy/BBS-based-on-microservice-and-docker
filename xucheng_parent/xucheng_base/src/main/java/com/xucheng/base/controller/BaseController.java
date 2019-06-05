package com.xucheng.base.controller;

import com.xucheng.base.pojo.Label;
import com.xucheng.base.service.BaseService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/1/16 17:48
 * @Version: 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/label")
public class BaseController {
    @Autowired
    BaseService baseService;


    @GetMapping("/type")
    public Result findAllType(){
        return new Result(true, StatusCode.OK,"查询成功",baseService.groupByType());
    }

    @GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",baseService.findAll());
    }
    @GetMapping("/{labelid}")
    public Result findById(@PathVariable("labelid") String labelid){
        return new Result(true,StatusCode.OK,"查询成功",baseService.findById(labelid));
    }
    @PostMapping
    public Result insert(Label label){
        baseService.insert(label);
        return new Result(true,StatusCode.OK,"插入成功");
    }

    @PutMapping("/follow/{id}/{follow}")
    public Result updateFans(@PathVariable String id, @PathVariable int follow){
        baseService.updateFans(id, follow);
        return new Result(true,StatusCode.OK,"更新成功");
    }

    @PutMapping
    public Result update(Label label){
        baseService.update(label);
        return new Result(true,StatusCode.OK,"更新成功");
    }
    @DeleteMapping("/{labelid}")
    public Result delete(@PathVariable("labelid") String labelid){
        baseService.delete(labelid);
        return new Result(true,StatusCode.OK,"删除成功");
    }
    @PostMapping("/search")
    public Result search(@RequestBody Label label){
        List<Label> labels = baseService.findBySearch(label);
        return new Result(true,StatusCode.OK,"查询成功",labels);
    }
    /** 
    * @Description:  带分页的查询
    * @Param: [label, page第几页, size大小]
    * @return: entity.Result 
    * @Author: yangxucheng 
    * @Date: 2019/1/17 16:16
    */ 
    @PostMapping("/search/{page}/{size}")
    public Result pageQuery(@RequestBody Label label, @PathVariable int page ,@PathVariable int size){
        Page<Label> labels = baseService.pageQuery(label,page,size);
        return new Result(true,StatusCode.OK,"查询成功", new PageResult<>(labels.getTotalElements(),labels.getContent()));
    }

}
