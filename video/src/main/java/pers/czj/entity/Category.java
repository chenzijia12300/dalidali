package pers.czj.entity;

import lombok.Data;

/**
 * 创建在 2020/7/10 22:37
 * 视频类别
 */

@Data
public class Category {

    /**
     * 自增主键
     */
    private long id;

    /**
     * 频道名
     */
    private String name;

    /**
     * 所属父频道主键（如果没有父类频道则为0）
     */
    private long pid;

    /**
     * 总作品数
     */
    private long proNum;
}
