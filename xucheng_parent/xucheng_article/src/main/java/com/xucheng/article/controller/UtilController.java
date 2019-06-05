package com.xucheng.article.controller;

import com.xucheng.article.pojo.Article;
import com.xucheng.article.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class UtilController {

	@Autowired
	private ArticleService articleService;

	/**
	* @Description: 图片上传公共部分
	* @Param: [file, number] number记录区别各个位置
	* @return: entity.Result
	* @Author: yangxucheng
	* @Date: 2019/3/4 10:49
	*/
	@PostMapping("/uploadImg/{number}")
	public Result uploadImg(@RequestParam(name = "img")MultipartFile file, @PathVariable("number") String number) {
		String filePath = "D:\\github\\xucheng_parent\\images";
		if(number.equals("1"))
			filePath += "\\articles";
		else
			filePath += "\\cover";
		String originalFilename = file.getOriginalFilename();
		String newFilename = UUID.randomUUID()+originalFilename;/*生成唯一图片名称*/
		System.out.println(newFilename);
		File targetFile = new File(filePath,newFilename);
		try {
			file.transferTo(targetFile);/*实现文件上传*/
			return new Result(true,StatusCode.OK,"图片上传成功",newFilename);
		} catch (IOException e) {
			e.printStackTrace();
			return new Result(false,StatusCode.ERROR,"图片上传失败");
		}
	}

	
}
