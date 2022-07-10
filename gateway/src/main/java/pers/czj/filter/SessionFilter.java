package pers.czj.filter;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import pers.czj.common.CommonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        String requestUri = request.getRequestURI();
        if (requestUri.contains("/login")) {
            log.info("进入登录接口");
            return null;
        }
        HttpSession httpSession = request.getSession();
        Object o = httpSession.getAttribute("USER_ID");
        if (ObjectUtils.isEmpty(o)) {
            //过滤该请求，不对其进行路由操作
            ctx.setSendZuulResponse(false);
            response.setHeader("Content-type", "application/json; charset=utf-8");
            try {
                response.getWriter().write(JSON.toJSONString(new CommonResult<>(403, "登录已过期，请重新登录~")));
                ctx.setResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            try {
//                response.getWriter().write(JSON.toJSONString(new CommonResult<>(403,"登录已过期，请重新登录~")));
//                return null;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return null;
        }
        String userId = o.toString();
        log.info("userId:{}", userId);
        Map<String, List<String>> params = ctx.getRequestQueryParams();
        if (CollectionUtils.isEmpty(params)) {
            params = new HashMap<>();
        }
        params.put("uid", Lists.newArrayList(userId));
        ctx.setRequestQueryParams(params);

        return null;
    }
}
