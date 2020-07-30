package pers.czj.constant;

/**
 * 创建在 2020/7/30 18:38
 */
public enum  DanmuLocationEnum {
    TOP(1),BOTTOM(2),STANDARD(0);
    int location;

    DanmuLocationEnum(int location) {
        this.location = location;
    }
}
