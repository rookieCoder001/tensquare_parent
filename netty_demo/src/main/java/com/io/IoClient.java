package com.io;
import java.net.Socket;

/**
 * 传统io模式客户端
 * @Author luo
 * @Date 2020/4/9 10:02
 */
public class IoClient {

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            new ClientDemo().start();
        }
    }

    static class ClientDemo extends Thread{
        @Override
        public synchronized void run() {
            try {
                //创建客户端套接字
                Socket socket = new Socket("127.0.0.1",8888);

                //发送消息 演示 每隔两秒像服务器发送消息
                while (true){
                    socket.getOutputStream().write("测试数据".getBytes());
                    socket.getOutputStream().flush();
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
