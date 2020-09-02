package pers.czj.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import pers.czj.utils.IPUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 创建在 2020/8/31 21:28
 */
@Configuration
public class TimePostFilter extends ZuulFilter {
    
    private static final Logger log = LoggerFactory.getLogger(TimePostFilter.class);

    @Value("${time.start}")
    private String startTimeKey;


    @Override
    public String filterType() {
        return "post";
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
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String uri = request.getRequestURI();
        String ip = IPUtils.getIpAddr(request);
        long startTime = (long)context.get(startTimeKey);
        long currentTime = System.currentTimeMillis();
        log.info("IP地址:[{}],请求:[{}]路径,共花费[{}]秒",ip,uri,(currentTime-startTime)/1000.0);
        return null;
    }
}
