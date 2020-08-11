package com.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统 io模式服务端
 *
 * @Author luo
 * @Date 2020/4/9 9:14
 */
public class IoServer {

    public static void main(String[] args) throws IOException {

        //创建服务器套接字,指定端口 并接收连接
        ServerSocket serverSocket = new ServerSocket(8888);

        while (true) {
            //阻塞方式获取连接
            Socket socket = serverSocket.accept();

            new Thread() {
                @Override
                public synchronized void run() {
                    try {

                        int len;
                        byte[] bytes = new byte[1024];
                        //流形式 持续接收消息
                        while ((len = socket.getInputStream().read(bytes)) != -1) {
                            //打印
                            System.out.println(Thread.currentThread().getName() + new String(bytes, 0, len));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
