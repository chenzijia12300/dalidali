package pers.czj.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建在 2020/7/17 22:01
 */
@Data
public class VideoBasicInfo implements Serializable {


    // 视频时长，单位：秒
    private Long duration;

    // 视频封面
    private String cover;

    // 视频压缩封面
    private String compressCover;

    // 视频宽度
    private Integer width;

    // 视频高度
    private Integer height;

    // 视频路径
    private String urls;



}
