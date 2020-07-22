package pers.czj.config;


import com.baomidou.mybatisplus.extension.api.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pers.czj.receiver.BaseReceiver;

/**
 * 创建在 2020/7/22 17:47
 */
@Configuration
public class RabbitConfig {

    @Autowired
    private BaseReceiver baseReceiver;

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    private static final Logger log = LoggerFactory.getLogger(RabbitConfig.class);

    @Bean
    public Queue queue(){

        return new Queue("dalidali-test-queue",true);
    }

    @Bean
    public Exchange exchange(){
        return new DirectExchange("dalidali-test-exchange",true,false);
    }

    @Bean
    public Binding binding(@Autowired Queue queue,@Autowired Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("test").noargs();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(@Autowired ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //开启回调函数
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, b, s) -> {
            log.info("数据:{}\n确认情况:{}\n原因:{}",correlationData,b,s);
        });
        rabbitTemplate.setReturnCallback((message, i, s, s1, s2) -> {
            log.info("消息:{}\n回应码:{}\n回应消息:{}");
        });

        return rabbitTemplate;
    }
    @Bean
    public Connection connection(@Autowired ConnectionFactory connectionFactory){
        return connectionFactory.createConnection();
    }

/*    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cachingConnectionFactory);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setQueueNames("dalidali-test-queue");
        container.setMessageListener(baseReceiver);
        return container;
    }*/
}
