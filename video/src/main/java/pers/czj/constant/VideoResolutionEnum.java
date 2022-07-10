package pers.czj.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

/**
 * 创建在 2020/7/11 21:58
 * 视频分辨率枚举类
 */
@Getter
public enum VideoResolutionEnum implements IEnum<Integer> {


    /*
        landscape 横屏视频分辨率
     */
    LANDSCAPE_360(0, "640", "360"),
    LANDSCAPE_480(1, "720", "480"),
    LANDSCAPE_720(2, "1280", "720"),
    LANDSCAPE_1080(3, "1920", "1080"),


    /*
        portrait 竖屏视频分辨率
     */

    PORTRAIT_360(4, "360", "640"),
    PORTRAIT_480(5, "480", "720"),
    PORTRAIT_720(6, "720", "1280"),
    PORTRAIT_1080(7, "1080", "1920");

    private int code;

    private String width;

    private String height;

    VideoResolutionEnum(int code, String width, String height) {
        this.code = code;
        this.width = width;
        this.height = height;
    }

    @Override
    public Integer getValue() {
        return code;
    }

    public int getCode() {
        return code;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    /*    public static VideoResolutionEnum getInstance(int code){
        for (VideoResolutionEnum stateEnum:VideoResolutionEnum.values()){
            if (stateEnum.code==code){
                return stateEnum;
            }
        }
        return null;
    }*/
}
