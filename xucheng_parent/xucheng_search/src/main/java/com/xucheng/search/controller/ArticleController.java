package com.xucheng.search.controller;

import com.xucheng.search.pojo.Article;
import com.xucheng.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/1/22 15:09
 * @Version: 1.0
 */
@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {
    @Autowired
    ArticleService articleService;

/*    @PostMapping
    public Result save(@RequestBody Article article){
        articleService.save(article);
        return new Result(true, StatusCode.OK,"插入成功");
    }*/
    /**
    * @Description:  查询，单个参数使用@RequestParam即可
    * @Param: [key, page, size]
    * @return: entity.Result
    * @Author: yangxucheng
    * @Date: 2019/1/22 16:41
    */
    @PostMapping("/{key}/{page}/{size}")
    public Result findByKey(@PathVariable String key,@PathVariable int page,@PathVariable int size){
        System.out.println(key);
        Page<Article> pageData = articleService.findByKey(key,page,size);
        return new Result(true,StatusCode.OK,"查询成功" ,new PageResult<Article>(pageData.getTotalElements(),pageData.getContent()));
    }

}
