package com.albert.concurrent.expand.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal搭配线程池使用的问题及解决方式
 *
 * @author Albert
 * @date 2021/3/7 上午1:06
 */
public class ThreadPoolDemo {

    private static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

//    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        for (int i = 0; i < 5; i++) {
//            executorService.submit(() -> {
//                Integer before = threadLocal.get();
//                threadLocal.set(threadLocal.get() + 1);
//                Integer after = threadLocal.get();
//                System.out.println("before :" + before + "，after：" + after);
//            });
//        }
//        executorService.shutdown();
//    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                try {
                    Integer before = threadLocal.get();
                    threadLocal.set(threadLocal.get() + 1);
                    Integer after = threadLocal.get();
                    System.out.println("before :" + before + "，after：" + after);
                } finally {
                    threadLocal.remove();
                }
            });
        }
        executorService.shutdown();
    }


}

/**
 * ThreadLocal搭配线程池问题：
 * 由于线程池内的线程是复用的，上一个线程执行完之后，对ThreadLocal设置的值下一个线程是可以获取到的，这就对下一个线程的结果造成了影响。
 * 解决办法：
 * 当前线程使用完ThreadLocal之后，即使使用remove()方法清理。
 */