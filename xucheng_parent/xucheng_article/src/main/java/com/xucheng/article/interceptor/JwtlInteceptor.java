package com.xucheng.article.interceptor;

import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @introduction: xucheng_parent
 * @Description: 这个拦截器只对带有token的请求进行处理，将结果放入request.attribute中供service验证，并不做拦截一律放行
 * 与aop类似做环绕处理
 * @Author: yangxucheng
 * @Date: 2019/1/24 16:05
 * @Version: 1.0
 */
@Component
public class JwtlInteceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("经过拦截器");
        //从头信息中获取token(安全)
        String header = request.getHeader("Authorization");//人为约定
        if(header!=null&&!header.equals("")){
            //人为添加前缀
            if(header.startsWith("yangxucheng ")){
                String token = header.substring(12);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    if(claims.get("roles")!=null ||claims.get("roles").equals("admin")){
                        request.setAttribute("claims_admin","admin");
                    }
                    if(claims.get("roles")!=null ||claims.get("roles").equals("user")){
                        request.setAttribute("claims_user","user");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    throw new RuntimeException("令牌有误");
                }
            }
        }

        return true;
    }
}
