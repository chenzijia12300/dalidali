package pers.czj.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pers.czj.entity.VideoCrawlerLog;

/**
 * 创建在 2020/10/12 15:00
 */
public interface CrawlerReceiverService {


    public void saveCrawlerLog(VideoCrawlerLog crawlerLog);
}
