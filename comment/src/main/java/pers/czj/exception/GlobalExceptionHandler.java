package pers.czj.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.czj.common.CommonResult;

import java.util.concurrent.TimeoutException;

/**
 * 创建在 2020/7/11 14:54
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({CommentException.class,ReplyException.class})
    public CommonResult handler(Exception e){
        log.error("抛出异常：{}",e.getMessage());
        return CommonResult.failed(e.getMessage());
    }


    @ExceptionHandler({InternalApiException.class, TimeoutException.class})
    public CommonResult internalApiException(Exception e){
        log.error("内部调用接口出错:{}",e.getMessage());
        return CommonResult.failed("服务器内部出现错误，快联系后端仔来修复~");
    }
}
