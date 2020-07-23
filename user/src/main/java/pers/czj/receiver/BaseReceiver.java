/*
package pers.czj.receiver;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

*/
/**
 * 创建在 2020/7/22 20:50
 *//*

@Component
public class BaseReceiver implements ChannelAwareMessageListener {
    
    private static final Logger log = LoggerFactory.getLogger(BaseReceiver.class);
    
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String msg = message.toString();
        log.info("消费端:{}",msg);
        channel.basicAck(deliveryTag,false);
    }


}
*/
