package pers.czj.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建在 2020/7/17 22:01
 */
@Data
public class VideoBasicInfo implements Serializable {

    private long duration;

    private String cover;

    private String width;

    private String height;

    private String url;
}
