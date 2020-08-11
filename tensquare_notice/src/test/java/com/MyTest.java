package com;

import com.tensquare.notice.NoticeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author luo
 * @Date 2020/4/9 23:55
 */
@SpringBootTest(classes = NoticeApplication.class)
@RunWith(SpringRunner.class)
public class MyTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void test01(){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
//        rabbitAdmin.declareQueue(new Queue("呵呵哒"));
        rabbitAdmin.purgeQueue("呵呵哒");
    }
}
