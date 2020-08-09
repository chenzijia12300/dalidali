package pers.czj.entity;

import lombok.Data;
import pers.czj.constant.DynamicType;

import java.util.Date;

/**
 * 创建在 2020/8/9 22:26
 * 动态
 */
@Data
public class Dynamic {

    /**
     * 动态主键
     */
    private long id;

    /**
     * 发送用户的主键
     */
    private long uid;


    /**
     * 对应事务(视频/专栏)的主键
     */
    private long oid;

    /**
     * 消息创建时间
     */
    private Date createTime;

    /**
     * 动态类型
     */
    private DynamicType type;


}
