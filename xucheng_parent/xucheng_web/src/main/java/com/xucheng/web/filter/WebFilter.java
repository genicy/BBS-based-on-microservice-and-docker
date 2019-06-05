package com.xucheng.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @introduction: xucheng_parent
 * @Description: ${description}
 * @Author: yangxucheng
 * @Date: 2019/2/21 11:06
 * @Version: 1.0
 */
@Component
public class WebFilter extends ZuulFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
    * 有些请求需要验证有些不需要，所以不在这里进行权限验证
    * */
    @Override
    public Object run() throws ZuulException {
        logger.info("经过Web过滤器");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = (HttpServletRequest) requestContext.getRequest();
        String Authorization = httpServletRequest.getHeader("Authorization");
        if(Authorization!=null||!"".equals(Authorization)){
            requestContext.addZuulRequestHeader("Authorization",Authorization);
        }
        return null;
    }
}
