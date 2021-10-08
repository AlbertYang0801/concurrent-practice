package com.albert.concurrent.expand.pattern.completablefuture;

import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture 练习
 * @author yangjunwei
 * @date 2021/9/28 7:38 下午
 */
public class AskThread implements Runnable {
    private CompletableFuture<Integer> completableFuture = null;

    public AskThread(CompletableFuture<Integer> completableFuture) {
        this.completableFuture = completableFuture;
    }

    @Override
    public void run() {
        try {
            int content = completableFuture.get() * completableFuture.get();
            System.out.println(content);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        new Thread(new AskThread(future)){}.start();
        //模拟结果的计算过程
        Thread.sleep(3000);
        //完成之后进行通知
        future.complete(50);
    }

}
