package pers.czj.service;

import pers.czj.entity.VideoCrawlerLog;

/**
 * 创建在 2020/10/12 14:55
 */
public interface CrawlerSendService {

    /**
     * 发送视频爬虫信息到消息队列中
     * @author czj
     * @date 2020/10/12 14:56
     * @param [log]
     * @return void
     */
    void send(VideoCrawlerLog log);
}
