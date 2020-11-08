package pers.czj.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import pers.czj.constant.VideoPublishStateEnum;
import pers.czj.constant.VideoResolutionEnum;
import pers.czj.constant.VideoScreenTypeEnum;
import pers.czj.entity.VideoLog;

import java.util.Date;

/**
 * 创建在 2020/7/13 18:19
 */
@Data
@ApiModel("视频详细信息传输输出类")
public class VideoDetailsOutputDto {

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
     * 评论数
     */
    private long commentNum;

    /**
     * 视频点赞量
     */
    private long praiseNum;

    /**
     * 视频硬币数
     */
    private long coinNum;

    /**
     * 视频的顶级（父）频道
     */
    private long categoryPId;

    /**
     * 视频所属频道
     */
    private long categoryId;

    /**
     * 秒数
     */
    private long length;


    /**
     * 添加于:2020/11/1
     * 视频宽度
     */
    private int width;


    /**
     * 添加于:2020/11/1
     * 视频高度
     */
    private int height;

    /**
     * 添加于:2020/11/1
     * 视频屏幕类型
     */
    private VideoScreenTypeEnum screenType;


    /**
     * up主头像
     */
    private String upImg;

    /**
     * up主名字
     */
    private String upName;


    /**
     *  视频的顶级频道（冗余）
     */
    private String categoryPName;

    /**
     * 视频所属频道（冗余）
     */
    private String categoryName;

    /**
     * 视频标题
     */
    private String title;

    /**
     * 视频基本地址，根据清晰度确定准确地址
     */
    private String urls;

    /**
     * 视频封面
     */
    private String cover;


    /**
     * 视频简介
     */
    private String description;

    /**
     * 标签字符串（用,分割）
     */
    private String tags;




    /**
     * 视频分辨率枚举类
     */
    private VideoResolutionEnum resolutionState;


    /**
     * 视频更改时间
     */
    private Date updateTime;

    /**
     * 用户记录
     */
    private VideoLog log;


    /**
     * 是否关注
     */
    private boolean isFollow;
}
