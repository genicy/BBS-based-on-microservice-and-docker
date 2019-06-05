package com.xucheng.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @introduction: 网站后台网关，管理员使用，与用户端不同，后台访问任何请求路径都得验证，所以在网关分发请求时便进行验证
 *  * 用户端具体请求再进行验证
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/2/21 11:06
 * @Version: 1.0
 */
@Component
public class ManagerFilter extends ZuulFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        /*pre请求路由前调用*/
        return "pre";
    }

    @Override
    public int filterOrder() {
        /*过滤器的执行顺序，数字越小，优先级越高*/
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        /*是否执行该过滤器*/
        return true;
    }
    /*header头信息经过网关会丢失
    * 这里经过过滤器后转发Authorization信息
    * */
    @Override
    public Object run() throws ZuulException {
        logger.info("经过后台过滤器");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = (HttpServletRequest) requestContext.getRequest();
        //一个请求对于zuul网关共有两次，第一次在内部找到对应的转发路径，第二次才是请求的路径，所以应当放行第一次
        if(httpServletRequest.getMethod().equals("OPTIONS")){
            return null;
        }
        //当为登录时，放过该请求
        //getRequestURL为完整路径，getRequestURI为部分路径
        String url = httpServletRequest.getRequestURL().toString();
        if(url.indexOf("login")>0){
            return null;
        }
        String Authorization = httpServletRequest.getHeader("Authorization");
        if(Authorization!=null&&Authorization.startsWith("yangxucheng")){
            String token = Authorization.substring(12);
            Claims claims = jwtUtil.parseJWT(token);
            String role = (String) claims.get("roles");
            if("admin".equals(role)){
                logger.info("验证通过");
                requestContext.addZuulRequestHeader("Authorization",Authorization);
                return null;
            }
        }
        requestContext.setSendZuulResponse(false);//终止运行
        requestContext.setResponseStatusCode(403);//http状态码，自己定义
        requestContext.setResponseBody("无权访问");
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");//否则”无权访问“将无法正常显示
        return null;
    }
}
