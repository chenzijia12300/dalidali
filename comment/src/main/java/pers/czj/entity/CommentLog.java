package pers.czj.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 创建在 2020/7/19 16:57
 */
@Data
public class CommentLog {

    private long id;

    private long cid;

    private long uid;

    @TableField(exist = false)
    private String tableName;

    public CommentLog(String tableName, long cid, long uid) {
        this.cid = cid;
        this.uid = uid;
        this.tableName = tableName;
    }

    public CommentLog() {
    }
}
