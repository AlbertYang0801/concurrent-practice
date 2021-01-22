package com.albert.concurrentpractice.book.chaptertwo;

import lombok.SneakyThrows;
import org.junit.Test;

/**
 * 线程优先级练习
 *
 * @author Albert
 * @date 2020/8/31 14:41
 */
public class ThreadPriority_10 {

    public static class HignPriority implements Runnable {

        private static int count = 0;

        @Override
        public void run() {
            while (true) {
                count++;
                if (count > 10000000) {
                    System.out.println("HignPriority 抢到了锁");
                    break;
                }
            }
        }
    }

    public static class LowPriority extends Thread {
        private static int count = 0;

        @Override
        public void run() {
            while (true) {
                count++;
                if (count > 10000000) {
                    System.out.println("LowPriority 抢到了锁");
                    break;
                }
            }
        }
    }

    /**
     * 测试设置线程优先级
     * 线程间抢夺资源，优先级越高，越容易抢到。
     */
    @SneakyThrows
    @Test
    public void testPriority() {
        Thread highThread = new Thread(new HignPriority());
        Thread lowThread = new Thread(new LowPriority());
        //设置优先级
        highThread.setPriority(Thread.MAX_PRIORITY);
        lowThread.setPriority(Thread.MIN_PRIORITY);
        lowThread.start();
        highThread.start();
        Thread.sleep(1000);
    }


}
