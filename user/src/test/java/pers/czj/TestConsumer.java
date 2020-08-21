//package pers.czj;
//
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
///**
// * 创建在 2020/7/22 19:19
// */
//@Component
//public class TestConsumer {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @RabbitHandler
//    public void process(String str){
//        System.out.println("消费者收到消息拉"+str);
//    }
//}
