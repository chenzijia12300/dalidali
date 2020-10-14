package pers.czj.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.czj.entity.VideoCrawlerLog;
import pers.czj.service.CrawlerReceiverService;
import pers.czj.service.VideoCrawlerLogService;

/**
 * 创建在 2020/10/12 15:12
 */
@Service
@RabbitListener(queues = "${mq.crawler_queue_name:CrawlerQueue}")
public class CrawlerReceiverServiceImpl implements CrawlerReceiverService {

    private static final Logger log = LoggerFactory.getLogger(CrawlerReceiverServiceImpl.class);

    @Autowired
    private VideoCrawlerLogService crawlerLogService;

    @RabbitHandler
    @Override
    public void saveCrawlerLog(VideoCrawlerLog crawlerLog) {
        boolean flag = crawlerLogService.save(crawlerLog);
        log.info("接受者:{}:{}",crawlerLog,flag);
    }
}
