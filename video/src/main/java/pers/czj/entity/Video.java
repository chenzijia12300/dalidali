package pers.czj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.czj.constant.VideoPublishStateEnum;
import pers.czj.constant.VideoResolutionEnum;
import pers.czj.constant.VideoScreenTypeEnum;

import java.util.Date;

/**
 * 创建在 2020/7/11 19:40
 * 视频对象(项目重点啊)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {

    /**
     * 视频自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 视频时长（秒为单位）
     */
    private long length;

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
     * 视频宽度
     */
    private int width;

    /**
     * 视频高度
     */
    private int height;


    /**
     * 预览图片
     */
    private String previewUrl;


    /**
     * 视频封面
     */
    private String cover;

    /**
     * 视频压缩封面
     */
    private String compressCover;

    /*    *//**
     *  视频的顶级频道（冗余）
     *//*
    private String categoryPName;

    *//**
     * 视频所属频道（冗余）
     *//*
    private String categoryName;*/


    /**
     * 视频标题
     */
    private String title;

    /**
     * 视频基本地址，根据清晰度确定准确地址
     */
    private String urls;

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
     * 视频发布状态
     */
    private VideoPublishStateEnum publishState;

    /**
     * 视频屏幕类型
     */
    private VideoScreenTypeEnum screenType;

    /**
     * 视频创建时间
     */
    private Date createTime;

    /**
     * 视频更改时间
     */
    private Date updateTime;
}
