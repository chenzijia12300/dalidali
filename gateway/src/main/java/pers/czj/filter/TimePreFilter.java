package pers.czj.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 创建在 2020/8/31 21:21
 */
@Configuration
public class TimePreFilter extends ZuulFilter {

    @Value("${time.start}")
    public String startTime;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        long currentTime = System.currentTimeMillis();
        RequestContext.getCurrentContext().set(startTime, currentTime);
        return null;
    }
}
