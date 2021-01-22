package com.albert.concurrentpractice.book.chaptertwo;

import com.albert.concurrentpractice.TestApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程等待wait()方法和线程唤醒notify()方法
 *
 * <p>
 *
 * @author Albert
 * @date 2020/8/26 19:16
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class ThreadWaitAndNotify_04 {

    final static Object object = new Object();

    public static class waitClass implements Runnable {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println("wait线程开始执行！");
                try {
                    System.out.println("线程开始等待");
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //该线程在wait()状态被notify()唤醒之后，会重新竞争锁对象，竞争到锁对象之后，才会继续执行。
                System.out.println("wait线程执行结束！");
            }
        }
    }

    public static class notifyClass implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            synchronized (object) {
                System.out.println("nofify线程开始执行！");
                //notify()方法，不会释放锁，而是通知进入wait()状态的线程可以重新竞争锁对象
                object.notify();
                System.out.println("nofity线程执行结束！");
                Thread.sleep(2000);
            }
        }
    }

    @SneakyThrows
    @Test
    public void testNotify() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 0,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        threadPoolExecutor.execute(new waitClass());
        threadPoolExecutor.execute(new notifyClass());
        threadPoolExecutor.shutdown();
        Thread.sleep(5000);
    }
    

}
