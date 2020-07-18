package pers.czj.exception;

/**
 * 创建在 2020/7/18 14:28
 */
public class ReplyException extends Exception{

    public ReplyException() {
    }

    public ReplyException(String message) {
        super(message);
    }

    public ReplyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReplyException(Throwable cause) {
        super(cause);
    }

    public ReplyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
