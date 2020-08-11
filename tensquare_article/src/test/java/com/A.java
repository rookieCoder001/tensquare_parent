package com;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @Author luo
 * @Date 2020/4/15 16:29
 */
public class A implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        return "呵呵哒";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Future<String> future = executorService.submit(new A());

        System.out.println(future.get());

        System.out.println(test());

        Student 张三 = new Student("张三", 18);
        Student 李四 = new Student("张三", 18);


    }

    public static int test(){
        int i =0;
        try {

            i=1;

            return i;
        }finally {
            i=2;

            return i;
        }
    }
}
