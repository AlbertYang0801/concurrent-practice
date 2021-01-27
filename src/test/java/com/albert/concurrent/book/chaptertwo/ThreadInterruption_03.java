package com.albert.concurrent.book.chaptertwo;

import com.albert.concurrent.TestApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 线程中断的练习
 * Java中Thread提供了关于线程中断的三个方法：
 * 1.interrupt() 中断线程
 * 2.isInterrupted() 判断线程中断的状态
 * 3.interrupted() 判断线程中断的状态，并重置中断标志(实际是调用了isInterrupted()方法，并传入中断标志数据)
 * <p>
 *
 * @author Albert
 * @date 2020/8/16 19:16
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class ThreadInterruption_03 {

    /**
     * 测试interrupt()方法
     * 进行线程中断
     * 注意:线程中没有判断线程是否中断的逻辑，所以日志不会中断打印
     */
    @SneakyThrows
    @Test
    public void testInterruption() {
        //开启新线程
        Thread thread = new Thread(() -> {
            while (true) {
                log.info("没有被中断");
                Thread.yield();
            }
        });
        thread.start();
        Thread.sleep(1000);
        //线程中断（线程中没有针对线程中断的处理逻辑，所以线程会一直继续执行）
        thread.interrupt();
        Thread.sleep(1000);
    }

    /**
     * 测试isInterrupted()方法
     * 获取线程中断的状态
     * 注意：线程中增加了对线程中断状态的判断，会执行线程中断的逻辑，日志打印会中断
     */
    @SneakyThrows
    @Test
    public void testIsInterruption() {
        log.info("测试线程中断状态");
        //开启新线程
        Thread interruptionThread = new Thread(() -> {
            while (true) {
                //判断线程是否被中断
                if (Thread.currentThread().isInterrupted()) {
                    log.info("线程终于被中断了，但是线程不会立即停止");
                    break;
                }
                log.info("没有被中断");
                Thread.yield();
            }
        });
        interruptionThread.start();
        Thread.sleep(100);
        //线程中断
        interruptionThread.interrupt();
        log.info("线程进行了中断操作，线程内的操作（日志打印）不会继续");
        Thread.sleep(100);
    }

    /**
     * 测试interrupted()方法，判断线程中断状态，会初始化中断标志
     * 实际调用了isInterrupted()方法，并传入了中断标志
     */
    @SneakyThrows
    @Test
    public void testInterrupted() {
        log.info("测试线程中断状态");
        //开启新线程
        Thread interruptionThread = new Thread(() -> {
            while (true) {
                //判断线程是否被中断
                if (Thread.interrupted()) {
                    log.info("线程终于被中断了，但是线程不会立即停止");
                    break;
                }
                log.info("没有被中断");
                Thread.yield();
            }
        });
        interruptionThread.start();
        Thread.sleep(100);
        //线程中断
        interruptionThread.interrupt();
        log.info("线程进行了中断操作，线程内的操作（日志打印）不会继续");
        Thread.sleep(100);
    }

    /**
     * 测试sleep()方法
     * 捕获sleep()方法的异常，并设置线程中断
     */
    @SneakyThrows
    @Test
    public void testSleep() {
        Thread thread = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    log.info("线程中断");
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //sleep()方法需要捕获 线程中断异常
                    e.printStackTrace();
                    //设置中断标志（捕获到异常之后，中断线程，使方法体不再继续执行）
                    Thread.currentThread().interrupt();
                }
                Thread.yield();
            }
        });
        thread.start();
        Thread.sleep(1000);
        //设置线程中断
        thread.interrupt();
    }


}
