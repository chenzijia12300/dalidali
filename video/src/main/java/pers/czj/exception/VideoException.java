package pers.czj.exception;

/**
 * 创建在 2020/7/13 17:46
 */
public class VideoException extends Exception {
    public VideoException() {
        super();
    }

    public VideoException(String message) {
        super(message);
    }

    public VideoException(String message, Throwable cause) {
        super(message, cause);
    }

    public VideoException(Throwable cause) {
        super(cause);
    }

    protected VideoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
