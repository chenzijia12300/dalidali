package pers.czj.entity;

import lombok.Data;
import pers.czj.constant.ActionType;

import java.util.Date;

/**
 * 创建在 2020/8/9 22:34
 * 消息
 */
@Data
public class Message {

    /**
     * 消息主键
     */
    private long id;

    /**
     * 发送用户主键
     */
    private long sendUid;

    /**
     * 解决用户主键
     */
    private long receiveUid;

    /**
     * 动作类型
     */
    private ActionType type;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    
}
