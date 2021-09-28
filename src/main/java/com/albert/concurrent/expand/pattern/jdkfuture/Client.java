package com.albert.concurrent.expand.pattern.jdkfuture;

import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Callable搭配FutureTask练习
 * 搭配线程池练习异步
 * @author yangjunwei
 * @date 2021/9/28 3:06 下午
 */
public class Client {

    @SneakyThrows
    public static void main(String[] args) {
        RealData realData = new RealData("hello");
        FutureTask<String> futureTask = new FutureTask<>(realData);
        //搭配线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(futureTask);
        System.out.println("提交任务结束");

        try {
            System.out.println("异步开始进行");
            Thread.sleep(5000);
            System.out.println("异步结束进行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //get()方法是阻塞的
        String content= futureTask.get();
        System.out.println("异步获取计算结果："+content);
        executorService.shutdown();
    }


}
