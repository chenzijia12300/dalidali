package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.entity.VideoLog;

/**
 * 创建在 2020/7/19 21:47
 */
public interface VideoLogService extends IService<VideoLog> {

    /**
     * @author czj
     * 动态处理视频的点赞/投币/收藏等记录操作
     * @date 2020/7/19 22:42
     * @param [entity]
     * @return boolean
     */
    public boolean handlerVideoLog(VideoLog entity);
}
