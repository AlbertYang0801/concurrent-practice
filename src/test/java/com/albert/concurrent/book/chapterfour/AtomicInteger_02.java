package com.albert.concurrent.book.chapterfour;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger的简单练习
 *
 * @author Albert
 * @date 2021/3/8 下午4:41
 */
public class AtomicInteger_02 {

    static CountDownLatch countDownLatch = new CountDownLatch(10);

    //线程不安全
//    static Integer num;

    //线程安全
    static AtomicInteger num = new AtomicInteger();

    static class AddThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
//                num++;
                num.incrementAndGet();
            }
            countDownLatch.countDown();
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new AddThread());
        }
        countDownLatch.await();
        System.out.println(num);
        executorService.shutdown();
    }


}


