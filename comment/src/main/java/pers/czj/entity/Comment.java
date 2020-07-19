package pers.czj.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * 创建在 2020/7/15 16:09
 */
@Data
public class Comment {

    /**
     * 评论主键
     */
    private long id;


    /**
     * 视频/专栏的主键
     */
    private long pid;


    /**
     * 评论人主键
     */
    private long uid;

    /**
     * 点赞数
     */
    private long praiseNum;

    /**
     * 回复数
     */
    private long replyNum;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private Date createTime;

    /**
     *  动态表名（冗余）
     */
    @TableField(exist = false)
    @TableLogic
    private String tableName;
}
