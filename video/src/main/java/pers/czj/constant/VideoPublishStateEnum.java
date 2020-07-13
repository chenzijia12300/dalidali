package pers.czj.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

/**
 * 创建在 2020/7/11 23:27
 * 视频发布状态枚举类
 */
@Getter
public enum  VideoPublishStateEnum implements IEnum<Integer> {
    //制作中        审核           未发布               发布            下架
    MAKING(0),AUDIT(1),UN_PUBLISH(2),PUBLISH(3),SOLD_OUT(4);

    private int code;

    VideoPublishStateEnum(int code) {
        this.code = code;
    }


    public static VideoPublishStateEnum getInstance(int code){
        for (VideoPublishStateEnum stateEnum:VideoPublishStateEnum.values()){
            if (stateEnum.code==code){
                return stateEnum;
            }
        }
        return null;
    }

    @Override
    public Integer getValue() {
        return code;
    }
}
