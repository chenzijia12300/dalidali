package pers.czj.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.czj.common.CommonResult;

/**
 * 创建在 2020/7/11 14:54
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CategoryException.class)
    public CommonResult handler(Exception e){
        log.info("抛出异常：{}",e.getMessage());
        return CommonResult.failed(e.getMessage());
    }
}
