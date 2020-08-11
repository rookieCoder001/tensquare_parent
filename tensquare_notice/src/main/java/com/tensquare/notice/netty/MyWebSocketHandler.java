package com.tensquare.notice.netty;


import com.alibaba.fastjson.JSON;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * webSocket处理器
 *
 * @Author luo
 * @Date 2020/4/9 22:31
 */
public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //队列名前缀 系统通知
    private static final String ARTICLE_SUBSCRIBE_QUEUE_ = "article_subscribe_queue_";
    //文章点赞通知 队列名
    private static final String ARTICLE_THUMBUP_QUEUE_="article_thumbup_queue_";

    // 送Spring容器中获取rabbit消息监听器容器,处理订阅消息sysNotice
    SimpleMessageListenerContainer container = ApplicationContextProvider.getApplicationContext().getBean("sysNoticeListenerContainer", SimpleMessageListenerContainer.class);

    // 送Spring容器中获取rabbit消息监听器容器,处理订阅消息sysNotice
    SimpleMessageListenerContainer container1 = ApplicationContextProvider.getApplicationContext().getBean("thumbupNoticeListenerContainer", SimpleMessageListenerContainer.class);

    //从Spring容器中获取RabbitTemplate
    RabbitTemplate rabbitTemplate = ApplicationContextProvider.getApplicationContext().getBean(RabbitTemplate.class);

    //存放WebSocket连接Map，根据用户id存放 从而判断用户是否在线
    public static ConcurrentMap<String, Object> map = new ConcurrentHashMap<>();


    /**
     * 用户请求webSocket的执行方法
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        //约定 用户第一次登录时 携带数据{"userId","1"}
        //获取请求参数消息
        String jsonData = msg.text();
        //解析json数据 获取userId
        String userId = (String) (JSON.parseObject(jsonData, Map.class).get("userId"));

        //从存储连接对象map获取该用户对应的连接对象
        Channel channel = (Channel) map.get(userId);
        //如果channel为null,就新建连接
        if (channel == null) {
            channel = ctx.channel();
            //把当前用户连接放入map容器中
            map.put(userId,channel);
        };

        //从消息队列中获取通知消息数量
        //获取消息管理器
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);

        //该用户对应消息队列中的消息数量
        int noticeCount = 0;
        Properties properties = rabbitAdmin.getQueueProperties(ARTICLE_SUBSCRIBE_QUEUE_ + userId);
        if (properties != null) {
            noticeCount = (int) properties.get("QUEUE_MESSAGE_COUNT");
        }


        //文章点赞的通知消息数量
        int thumbupNoticeCount = 0;
        Properties properties1 = rabbitAdmin.getQueueProperties(ARTICLE_THUMBUP_QUEUE_ + userId);
        if (properties1 != null) {
            thumbupNoticeCount = (int) properties.get("QUEUE_MESSAGE_COUNT");
        }


        //后台数据封装map
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("sysNoticeCount", noticeCount);
        resultMap.put("userNoticeCount", thumbupNoticeCount);
        Result result = new Result(true, StatusCode.OK, "查询成功", resultMap);

        //发送消息到前台
        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(result)));

        //因为上面代码中对队列中的数据没有进行消费  只是获取属性 故需要删除消息 避免统一消息的重复消费
        if (noticeCount>0){
            rabbitAdmin.purgeQueue(ARTICLE_SUBSCRIBE_QUEUE_+userId,true);
        }

        if (thumbupNoticeCount>0){
            rabbitAdmin.purgeQueue(ARTICLE_THUMBUP_QUEUE_+userId,true);
        }

        //为用户的消息通知队列注册监听器，便于用户在线的时候
        //一旦有消息，可以主动推送给用户，不需要用户请求服务器获取数据
        container.addQueueNames(ARTICLE_SUBSCRIBE_QUEUE_+userId);
        container1.addQueueNames(ARTICLE_THUMBUP_QUEUE_+userId);
    }
}
