package pers.czj.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;

/**
 * 创建在 2020/11/4 19:33
 */
public class InternalApiException extends HystrixBadRequestException {


    public InternalApiException(String message) {
        super(message);
    }

    public InternalApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
