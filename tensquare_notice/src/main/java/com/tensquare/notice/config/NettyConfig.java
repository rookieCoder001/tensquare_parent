package com.tensquare.notice.config;

import com.tensquare.notice.netty.NettyServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author luo
 * @Date 2020/4/9 21:20
 */
@Configuration
public class NettyConfig {

    @Bean
    public NettyServer CreateNettyServer(){

        NettyServer nettyServer = new NettyServer();

        //开启一个新线程启动 nettyServer
        new Thread(){
            @Override
            public void run() {
                nettyServer.start(1234);
            }
        }
        .start();

        return nettyServer;
    }

}
