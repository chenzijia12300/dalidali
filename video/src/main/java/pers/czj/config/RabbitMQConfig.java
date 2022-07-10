package pers.czj.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建在 2020/10/12 14:34
 */
@Configuration
public class RabbitMQConfig {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConfig.class);

    @Value("${mq.crawler_queue_name:CrawlerQueue}")
    private String queueName;

    @Value("${mq.crawler_exchange_name:CrawlerExchange}")
    private String exchangeName;

    @Value("${mq.crawler_routing_key:CrawlerKey}")
    private String routingKey;

    @Bean
    public Queue queue() {
        log.info("创建队列:{}", queueName);
        /**
         * durable:代表队列是否持久化
         * exclusive:代表队列是否独占，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
         * autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除
         */
        return new Queue(queueName, true);
    }

    @Bean
    public DirectExchange directExchange() {
        log.info("创建交换器:{}", exchangeName);
        return new DirectExchange(exchangeName, true, false);
    }


    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(routingKey);
    }


}
