package com.albert.concurrent.expand.pattern.completablefuture;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture 实现异步
 *
 * @author yangjunwei
 * @date 2021/9/28 7:43 下午
 */
public class AsyncCalc {

    @SneakyThrows
    public static Integer calc(int num) {
        Thread.sleep(3000);
//        return num * num;
        return num / 0;
    }

    @SneakyThrows
    @Test
    public void testSupplyAsync() {
        //supplyAsync 异步计算
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> calc(100));
        //get()方法会阻塞
        System.out.println(completableFuture.get());
    }

    @SneakyThrows
    @Test
    public void testThenApply() {
        //流式调用
        CompletableFuture<String> voidCompletableFuture = CompletableFuture.supplyAsync(() -> calc(100))
                //任务完成后的回调方法
                .thenApply((value) -> {
                    System.out.println("异步任务计算完成！！！");
                    return String.valueOf(value + 1);
                });
        //阻塞等待
        System.out.println("阻塞主线程:"+voidCompletableFuture.get());
    }

    @SneakyThrows
    @Test
    public void testThenAccept() {
        //流式调用
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> calc(100))
                //任务完成后的回调方法
                .thenApply((value) -> {
                    System.out.println("异步任务计算完成！！！");
                    return String.valueOf(value + 1);
                }).thenAccept(System.out::println);
        //阻塞等待
        System.out.println("阻塞主线程:"+voidCompletableFuture.get());
    }

    @Test
    public void testExce() throws InterruptedException {
        //流式调用增加异常处理
        CompletableFuture.supplyAsync(() -> calc(30))
                .exceptionally(ex -> {
                    System.out.println(ex.getMessage());
                    return 0;
                }).thenAccept(System.out::println);
        Thread.sleep(5000);
    }


}
