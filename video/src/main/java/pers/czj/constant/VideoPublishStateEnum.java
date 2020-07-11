package pers.czj.constant;

/**
 * 创建在 2020/7/11 23:27
 * 视频发布状态枚举类
 */
public enum  VideoPublishStateEnum {
    //制作中        审核           未发布               发布            下架
    MAKING(0),AUDIT(1),UN_PUBLISH(2),PUBLISH(3),SOLD_OUT(4);

    private int code;

    VideoPublishStateEnum(int code) {
        this.code = code;
    }
}
