package pers.czj.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

/**
 * 创建在 2020/7/11 21:58
 * 视频分辨率枚举类
 */
@Getter
public enum  VideoResolutionEnum implements IEnum<Integer> {
    P_360(0),P_480(1),P_720(2),P_1080(3);

    private int code;

    VideoResolutionEnum(int code) {
        this.code = code;
    }

    @Override
    public Integer getValue() {
        return code;
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
