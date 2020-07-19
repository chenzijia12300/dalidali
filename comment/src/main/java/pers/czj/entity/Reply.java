package pers.czj.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * 创建在 2020/7/15 16:32
 */
@Data
public class Reply {

    /**
     * 回复主键
     */
    private long id;

    /**
     * 对应评论主键
     */
    private long cid;

    /**
     * 回复目标的用户的主键
     */
    private long ruid;

    /**
     * 回复人主键
     */
    private long uid;

    /**
     * 点赞数
     */
    private long praiseNum;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 动态表名(冗余)
     */
    @TableField(exist = false)
    private String tableName;

}
