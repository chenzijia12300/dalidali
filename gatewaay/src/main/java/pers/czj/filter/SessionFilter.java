package pers.czj.filter;

import com.google.common.collect.Lists;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class SessionFilter extends ZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(SessionFilter.class);

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
        HttpServletRequest request = ctx.getRequest();
        HttpSession httpSession = request.getSession();
        Object o= httpSession.getAttribute("USER_ID");
        if (ObjectUtils.isEmpty(o)){
            log.info("没有登录记录");
            /*return null;*/
            o = "1";
        }
        String userId = o.toString();
        log.info("userId:{}",userId);
        Map<String, List<String>> params = ctx.getRequestQueryParams();
        if (CollectionUtils.isEmpty(params)){
            params = new HashMap<>();
        }
        params.put("uid", Lists.newArrayList(userId));
        ctx.setRequestQueryParams(params);
        return null;
    }
}
