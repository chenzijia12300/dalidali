package pers.czj.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pers.czj.entity.VideoCrawlerLog;
import pers.czj.service.CrawlerSendService;

/**
 * 创建在 2020/10/12 14:56
 */
@Service
public class CrawlerSendServiceImpl implements CrawlerSendService {

    private static final Logger log = LoggerFactory.getLogger(CrawlerSendServiceImpl.class);

    private RabbitTemplate rabbitTemplate;

    @Value("${mq.crawler_routing_key:CrawlerKey}")
    private String routingKey;

    @Value("${mq.crawler_exchange_name:CrawlerExchange}")
    private String exchangeName;

    @Autowired
    public CrawlerSendServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void send(VideoCrawlerLog crawlerLog) {
        log.info("发送视频爬虫信息:{}", crawlerLog);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, crawlerLog);
    }
}
