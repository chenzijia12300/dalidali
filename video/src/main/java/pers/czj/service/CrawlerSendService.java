package pers.czj.service;

import pers.czj.entity.VideoCrawlerLog;

/**
 * 创建在 2020/10/12 14:55
 */
public interface CrawlerSendService {

    /**
     * 发送视频爬虫信息到消息队列中
     *
     * @param [log]
     * @return void
     * @author czj
     * @date 2020/10/12 14:56
     */
    void send(VideoCrawlerLog log);
}
