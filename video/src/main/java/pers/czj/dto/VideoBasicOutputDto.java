package pers.czj.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 创建在 2020/7/17 10:38
 * 视频基本信息传输输出类
 */
@Data
@ApiModel("视频基本信息传输输出类")
public class VideoBasicOutputDto {
    /**
     * 视频自增主键
     */
    private long id;

    /**
     * 作者主键
     */
    private long uid;

    /**
     * 视频播放量
     */
    private long playNum;

    /**
     * 弹幕数（别问我为什么是拼音）
     */
    private long danmuNum;


    /**
     * up主头像
     */
    private String upImg;

    /**
     * up主名字
     */
    private String upName;

    /**
     * 视频标题
     */
    private String title;

    /**
     * 视频封面
     */
    private String cover;


}
