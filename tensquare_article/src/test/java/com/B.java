package com;

import org.aspectj.weaver.ast.Var;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author luo
 * @Date 2020/4/16 12:52
 */
public class B {

    @Test
    @Transactional
    public void test01(){

        System.out.println(""+Runtime.getRuntime().maxMemory()/1024/1024);
        System.out.println(Runtime.getRuntime().totalMemory()/1024/1024);

       byte[] bytes= new byte[1024*1024*1024];

       //-Xms内存大小 设置堆内存的初始大小 m memory s start -X固定
        // -Xmx内存大小 设置堆内存的最大内存大小
        //-XX:+PrintGCDetails 输出详细的GC处理日志

    }

    @Test
    public void test02(){

        new Thread(){
           private ThreadLocal<Object> local = new ThreadLocal<>();
            @Override
            public void run() {
                local.set("呵呵哒");
                System.out.println(Thread.currentThread().getName());
            }
        }.start();

        new Thread(){
            private ThreadLocal<Object> local = new ThreadLocal<>();
            @Override
            public void run() {
                System.out.println(local.get());
                System.out.println(Thread.currentThread().getName());
            }
        }.start();

    }


    @Test
    public int test03(){

//        for (;;){
//            System.out.println("1111");
//        }
        int i =0;
        try {

            i=1;

            return i;
        }finally {
            i=2;
        }

    }

    @Test
    public void test04() {

        System.out.println(test03());
    }

}
