package com.tensquare.notice.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
/**
 * @Author luo
 * @Date 2020/4/11 12:06
 */
@Component
public class RabbitListenerTest {

    @Autowired
    private RedisTemplate redisTemplate;

    int i =0;

    @RabbitListener(queues = "呵呵哒")
    public void listen(Message message, Channel channel) {

        try {
            System.out.println(new String(message.getBody()));
            System.out.println(message.getMessageProperties().getDeliveryTag());
                channel.basicNack(1,false,true);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
