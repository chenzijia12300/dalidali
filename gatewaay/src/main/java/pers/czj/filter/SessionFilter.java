/*
package pers.czj.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

*/
/**
 * 创建在 2020/7/14 18:34
 *//*

@Configuration
public class SessionFilter extends ZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(SessionFilter.class);

    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    HttpServletResponse httpServletResponse;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        String sessionId = httpServletRequest.getSession().getId();
        Cookie[] cookies = httpServletRequest.getCookies();
        for(Cookie cookie:cookies){
            log.debug("cookie:{}",cookie.getValue());
        }
        log.info("拦截"+sessionId);
        ctx.addZuulRequestHeader("Cookie", "SESSION=" + sessionId);
        ctx.setSendZuulResponse(true);// 对该请求进行路由
        ctx.setResponseStatusCode(200); // 返回200正确响应
        return null;
    }
}
*/
