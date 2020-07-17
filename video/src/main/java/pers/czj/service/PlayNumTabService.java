package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.redis.core.ZSetOperations;
import pers.czj.entity.PlayNumTab;

import java.util.Set;

/**
 * 创建在 2020/7/17 22:52
 */
public interface PlayNumTabService extends IService<PlayNumTab> {

    /**
     * @author czj
     * 批量添加播放记录
     * @date 2020/7/17 22:55
     * @param [set]
     * @return boolean
     */
    public boolean addAll(Set<ZSetOperations.TypedTuple> set);
}
