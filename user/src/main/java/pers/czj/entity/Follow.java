package pers.czj.entity;

import lombok.Data;

import java.util.Date;

/**
 * 创建在 2020/7/22 13:23
 */
@Data
public class Follow {

    private long id;

    private long uid;

    private long fuid;

    private Date createTime;

    public Follow(long uid, long fuid) {
        this.uid = uid;
        this.fuid = fuid;
    }

    public Follow() {
    }
}
