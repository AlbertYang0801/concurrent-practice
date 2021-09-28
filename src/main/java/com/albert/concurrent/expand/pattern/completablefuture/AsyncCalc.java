package com.albert.concurrent.expand.pattern.completablefuture;

import lombok.SneakyThrows;

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
//        return num*num;
        return num / 0;
    }

    @SneakyThrows
    public static void main(String[] args) {
        //supplyAsync 异步计算
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> calc(100));
        //get()方法会阻塞
        System.out.println(completableFuture.get());

        //--------------------------------------------------------
        //流式调用
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> calc(100))
                .thenAccept((value) -> String.valueOf(value + 1))
                .thenAccept(System.out::println);
        //阻塞等待
        System.out.println(voidCompletableFuture.get());
        //流式调用增加异常处理
        CompletableFuture.supplyAsync(() -> calc(30))
                .exceptionally(ex -> {
                    System.out.println(ex.toString());
                    return 0;
                }).thenAccept(System.out::println);

    }


}
