package pers.czj.entity;

import lombok.Data;

import java.util.Date;

/**
 * 创建在 2020/7/20 22:37
 */
@Data
public class UserCollectionLog {

    private long id;

    private long uid;

    private long vid;

    private Date createTime;

    public UserCollectionLog(long uid, long vid) {
        this.uid = uid;
        this.vid = vid;
    }

    public UserCollectionLog() {
    }
}
