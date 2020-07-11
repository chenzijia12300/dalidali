package pers.czj.constant;

import lombok.Getter;

/**
 * 创建在 2020/7/11 21:58
 * 视频分辨率枚举类
 */
@Getter
public enum  VideoResolutionEnum {
    P_360(0),P_480(1),P_720(2),P_1080(3);

    private int code;

    VideoResolutionEnum(int code) {
        this.code = code;
    }
}
