package pers.czj.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建在 2020/7/17 22:01
 */
@Data
public class VideoBasicInfo implements Serializable {


    private Long duration;

    private String cover;

    private Integer width;

    private Integer height;

    private String url;


}
