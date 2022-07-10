package pers.czj.common;

import lombok.Getter;

/**
 * 创建在 2020/6/6 22:11
 */
@Getter
public enum ResultCode {

    SUCCESS(200, "操作成功！"), FAILED(500, "服务器出现故障，操作失败！");

    private int code;

    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
