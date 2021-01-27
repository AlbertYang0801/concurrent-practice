package com.albert.concurrent.book.chapterthree;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 扩展线程池的练习
 * @author Albert
 * @date 2021/1/18 下午2:42
 */
public class ExtThreadPool_13 {

    public static class ExtMyTask implements Runnable {

        public String name;

        public ExtMyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + " task is run");
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1000)) {

            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行：" + ((ExtMyTask) r).name);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行结束：" + ((ExtMyTask)r).name);
            }

            @Override
            protected void terminated() {
                System.out.println("线程池退出");
            }
        };

        for (int i = 0; i < 10; i++) {
            ExtMyTask task = new ExtMyTask("TASK_" + i);
            executorService.execute(task);
        }
        executorService.shutdown();
    }


}
