package pers.czj.entity;

import lombok.Data;

import java.util.Date;

/**
 * 创建在 2020/7/17 22:50
 */
@Data
public class PlayNumTab {

    /**
     * 对应主键
     */
    private long id;

    /**
     * 视频主键
     */
    private long vid;

    /**
     * 播放量
     */
    private long num;

    /**
     * 对应时间
     */
    private Date createTime;

    public PlayNumTab(long vid, long num) {
        this.vid = vid;
        this.num = num;
    }
}
