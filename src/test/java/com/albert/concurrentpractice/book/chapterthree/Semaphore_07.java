package com.albert.concurrentpractice.book.chapterthree;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量的练习
 *
 * @author Albert
 * @date 2021/1/12 下午4:11
 */
public class Semaphore_07 implements Runnable {

    /**
     * 设置初始许可数量，和是否公平
     */
    private static Semaphore semaphore = new Semaphore(5, true);

    private int i;

    public Semaphore_07(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        try {
            //请求访问许可，信号量计数器-1
            semaphore.acquire();
            Thread.sleep(2000);
            System.out.println(i + " job done");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //归还访问许可，信号量计数器+1
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        Semaphore_07 runnable;
        for (int i = 0; i < 20; i++) {
            runnable = new Semaphore_07(i);
            executorService.submit(runnable);
        }
        executorService.shutdown();
    }


}
