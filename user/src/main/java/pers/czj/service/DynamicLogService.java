package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.entity.DynamicLog;

/**
 * 创建在 2020/10/14 16:41
 */
public interface DynamicLogService extends IService<DynamicLog> {

    /**
     * 检查是否有点赞信息
     *
     * @param did 动态主键
     * @param uid 用户主键
     * @return boolean
     * @author czj
     * @date 2020/10/14 17:14
     */
    @Deprecated
    boolean hasPraise(long did, long uid);


}
