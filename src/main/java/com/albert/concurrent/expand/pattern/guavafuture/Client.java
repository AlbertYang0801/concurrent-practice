package com.albert.concurrent.expand.pattern.guavafuture;

import com.albert.concurrent.expand.pattern.jdkfuture.RealData;
import com.google.common.util.concurrent.*;
import lombok.SneakyThrows;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * 使用Guava的增强Future模式
 * 解决JDK的Future模式的阻塞问题
 *
 * @author yangjunwei
 * @date 2021/9/28 3:06 下午
 */
public class Client {

    /**
     * 使用Guava的增强线程池和增强Future实现异步自动通知
     */
    @SneakyThrows
    @Test
    public void guavaFuture() {

        //使用Guava的增强线程池
        ListeningExecutorService listeningExecutorService =
                MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        RealData task = new RealData("hello");
        //提交任务
        ListenableFuture<String> future = listeningExecutorService.submit(task);

        //为异步任务增加监听，任务完成自动调用回调方法
        future.addListener(() -> {
            try {
                System.out.println("异步任务执行结束");
                String content = future.get();
                System.out.println("调用回调方法结果为：" + content);
                //关闭线程池
                listeningExecutorService.shutdown();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, listeningExecutorService);

        //非阻塞进行其他任务
        Thread.sleep(20000);
        System.out.println("进行其他业务处理");
    }


    /**
     * 增加对异常的处理
     */
    @SneakyThrows
    @Test
    public void guavaFutureErr() {

        //使用Guava的增强线程池
        ListeningExecutorService listeningExecutorService =
                MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        RealData task = new RealData("hello");
        //提交任务
        ListenableFuture<String> future = listeningExecutorService.submit(task);

        //增加任务的监听，同时增加成功和失败时的回调方法
        Futures.addCallback(future, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String s) {
                try {
                    System.out.println("异步任务执行结束");
                    String content = future.get();
                    System.out.println("调用回调方法结果为：" + content);
                    //关闭线程池
                    listeningExecutorService.shutdown();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("回调失败！！！！！！");
            }
        });

        //非阻塞进行其他任务
        Thread.sleep(5000);
        System.out.println("进行其他业务处理");
    }


}
