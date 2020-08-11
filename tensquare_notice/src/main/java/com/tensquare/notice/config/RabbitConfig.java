package com.tensquare.notice.config;

import com.tensquare.notice.listener.SysNoticeListener;
import com.tensquare.notice.listener.ThumbupNoticeListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq配置类
 * @Author luo
 * @Date 2020/4/10 0:12
 */
@Configuration
public class RabbitConfig {

    /**
     * 系统通知的消息监听容器
     * @param connectionFactory
     * @return
     */
    @Bean("sysNoticeListenerContainer")
    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory) {

        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory);

        //使用channel
        listenerContainer.setExposeListenerChannel(true);

        //设置自己的消息监听器
        listenerContainer.setMessageListener(new SysNoticeListener());

        return  listenerContainer;
    }


    /**
     * 用户点赞通知的消息监听容器
     * @param connectionFactory
     * @return
     */
    @Bean("thumbupNoticeListenerContainer")
    public SimpleMessageListenerContainer simpleMessageListenerContainer1(ConnectionFactory connectionFactory) {

        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory);

        //使用channel
        listenerContainer.setExposeListenerChannel(true);

        //设置自己的消息监听器
        listenerContainer.setMessageListener(new ThumbupNoticeListener());

        return  listenerContainer;
    }
}
