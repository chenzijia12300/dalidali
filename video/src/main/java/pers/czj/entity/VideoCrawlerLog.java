package pers.czj.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建在 2020/10/12 14:09
 */
@Data
public class VideoCrawlerLog implements Serializable {

    /**
     * 爬虫主键
     */
    private int id;


    /**
     * 爬取的视频标题
     */
    private String title;

    /**
     * 爬取的视频路径
     */
    private String url;


    /**
     * 爬取花费时间
     */
    private long time;


}
