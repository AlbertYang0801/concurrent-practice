package com.albert.concurrentpractice.book.chapterthree;

import java.util.concurrent.*;

/**
 * 自定义线程工厂的练习
 *
 * @author Albert
 * @date 2021/1/18 下午2:32
 */
public class ThreadFactory_12 {

    public static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println("task is run");
        }
    }

    public static void main(String[] args) {
        //创建自定义线程工厂，创建线程池
        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setDaemon(true);
                        System.out.println("thread create");
                        return thread;
                    }
                }
        );
        MyTask myTask = new MyTask();
        for (int i = 0; i < 5; i++) {
            executorService.submit(myTask);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }


}
