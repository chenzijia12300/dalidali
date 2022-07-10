package pers.czj.config;

import cn.hutool.core.util.StrUtil;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 创建在 2020/11/4 18:50
 */
@Configuration
public class FeignErrorDecoder implements ErrorDecoder {


    public FeignErrorDecoder() {
        log.info("feign全局创建");
    }

    private static final Logger log = LoggerFactory.getLogger(FeignErrorDecoder.class);

    @Override
    public Exception decode(String s, Response response) {
        log.info("s:{},response:{}", s, response);
        Response.Body body = response.body();
        String bodyStr = null;
        if (!ObjectUtils.isEmpty(body)) {
            try {
                bodyStr = Util.toString(body.asReader(Charset.forName("UTF-8")));
                log.info("bodyStr:{}", bodyStr);
                if (StrUtil.isNotBlank(bodyStr)) {
                    // DO SOME THING
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new InternalException(bodyStr);
    }
}
