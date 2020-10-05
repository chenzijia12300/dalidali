package pers.czj.entity;

import lombok.Data;
import pers.czj.constant.DanmuLocationEnum;

/**
 * 创建在 2020/7/30 18:35
 * 弹幕
 */
@Data
public class Danmu {

    private long id;

    /**
     * 对应视频主键
     */
    private long vid;

    /**
     * 废除：弹幕显示在第几秒
     * 弹幕显示在多少毫秒
     */
    private long showSecond;

    /**
     * 发表人
     */
    private long uid;

    /**
     * 弹幕显示位置
     */
    private DanmuLocationEnum location;

    /**
     * 十六进制颜色
     */
    private String color;


    /**
     * 内容
     */
    private String content;
}
