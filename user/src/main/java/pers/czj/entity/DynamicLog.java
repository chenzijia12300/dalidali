package pers.czj.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 创建在 2020/10/14 16:36
 */
@Data
@Accessors(chain = true)
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


    /**
     * 是否收藏
     */
    private boolean isCollection;


    /**
     * 创建默认的点赞对象
     *
     * @param []
     * @return pers.czj.entity.DynamicLog
     * @author czj
     * @date 2020/10/14 18:45
     */
    public static DynamicLog createDefaultPraiseObj() {
        DynamicLog log = new DynamicLog();
        log.setPraise(true);
        return log;
    }

}
