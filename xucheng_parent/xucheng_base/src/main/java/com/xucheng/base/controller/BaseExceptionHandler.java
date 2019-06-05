package com.xucheng.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.*;

/**
 * @introduction: xucheng_parent
 * @Description: controller 增强器
 * @Author: yangxucheng
 * @Date: 2019/1/16 20:41
 * @Version: 1.0
 */
@ControllerAdvice
public class BaseExceptionHandler {
    /** 
    * @Description: 全局异常捕捉 
    * @Param: [e] 
    * @return: entity.Result 
    * @Author: yangxucheng 
    * @Date: 2019/1/16 20:45
    */ 
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
