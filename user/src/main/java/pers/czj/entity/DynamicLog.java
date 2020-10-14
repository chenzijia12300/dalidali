package pers.czj.entity;

import lombok.Data;

import java.util.Date;

/**
 * 创建在 2020/10/14 16:36
 */
@Data
public class DynamicLog {

    /**
     * 动态记录主键
     */
    private long id;

    /**
     * 用户主键
     */
    private long uid;

    /**
     * 动态主键
     */
    private long did;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否点赞
     */
    private boolean isPraise;


    /**
     * 是否转发
     */
    private boolean isForward;
}
