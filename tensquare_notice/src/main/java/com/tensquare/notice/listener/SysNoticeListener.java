package com.tensquare.notice.listener;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.notice.netty.ApplicationContextProvider;
import com.tensquare.notice.netty.MyWebSocketHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.util.HashMap;

/**
 * 系统通知消息  rabbit监听类
 * @Author luo
 * @Date 2020/4/9 23:26
 */
public class SysNoticeListener implements ChannelAwareMessageListener {


    /**
     * 监听每个用户队列  查询是否有通知消息  有就推送数量到前台
     * @param message
     * @param channel
     * @throws Exception
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {


        //获取用户id
        String queue = message.getMessageProperties().getConsumerQueue();

        //切割队列名字符串  获取用户id
        String userId = queue.substring(queue.lastIndexOf("_") + 1);
        //判断用户是否连接
        io.netty.channel.Channel nettyChannel = (io.netty.channel.Channel) MyWebSocketHandler.map.get(userId);

        if (nettyChannel!=null && nettyChannel.isActive()){
            //用户已经登录
            HashMap<String, Object> resultMap = new HashMap<>();
            //通知数量+1
            resultMap.put("sysNoticeCount",1);
            //主动推送数据到前台
            nettyChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(new Result(true, StatusCode.OK,"查询成功",resultMap))));
        }else{
            //用户退出 或连接异常

            //删除该用户连接对象
            MyWebSocketHandler.map.remove(userId);
            //监听器容器移除该队列得监听
            SimpleMessageListenerContainer container = ApplicationContextProvider.getApplicationContext().getBean("sysNoticeListenerContainer", SimpleMessageListenerContainer.class);
            container.removeQueueNames(queue);

            //让消息重写返回消息队列
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
            //消息标识到达4之后
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }
    }
}
