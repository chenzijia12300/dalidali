package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.entity.VideoCrawlerLog;

/**
 * 创建在 2020/10/12 14:22
 */
public interface VideoCrawlerLogService extends IService<VideoCrawlerLog> {

    /**
     * 检查是否存在
     * @author czj
     * @date 2020/10/12 17:57
     * @param [url]
     * @return boolean
     */
    public boolean exists(String url);
}
