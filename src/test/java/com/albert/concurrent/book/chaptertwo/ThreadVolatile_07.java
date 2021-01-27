package com.albert.concurrent.book.chaptertwo;

import com.albert.concurrent.TestApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * volatile关键字练习
 * 1.volatile关键字的特点
 * (1).volatile变量可保证可见性，但不保证原子性。
 * volatile修饰变量时，会把该线程本地内存中的该变量刷新到主存中。
 * (2).volatile变量会禁止指令重排
 *
 * 2.volatile不适合的场景
 * volatile不适合复合操作（不能保证原子性）
 * 解决办法：加锁
 * <p>
 * 3.多线程并发的三个特点：
 * 1.原子性
 * 2.可见性
 * 3.有序性
 * <p>
 *
 * @author Albert
 * @date 2020/8/30 19:16
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class ThreadVolatile_07 {

    private static volatile int i = 0;

    /**
     * volatile关键字不能保证复合操作的原子性。
     * volatile关键字不能替代锁。
     * 实现效果：若线程内操作具有原子性，最后i的累加结果会等于100000，实际上i的结果会小于100000
     */
    @SneakyThrows
    @Test
    public void testVolatile() {
        Thread[] threads = new Thread[10];
        for (int j = 0; j < 10; j++) {
            threads[j] = new Thread(() -> {
                for (int k = 0; k < 10000; k++) {
                    i++;
                }
            });
            threads[j].start();
        }
        for (int j = 0; j < 10; j++) {
            threads[j].join();
        }
        log.info("多个线程最后的累加操作为:{}", i);
    }

    /**
     * 加锁保证原子性
     * Synchronized
     */
    @Test
    @SneakyThrows
    public void testVolatileSynchronized() {
        Thread[] threads = new Thread[10];
        for (int j = 0; j < 10; j++) {
            threads[j] = new Thread(() -> {
                synchronized (this) {
                    for (int k = 0; k < 10000; k++) {
                        i++;
                    }
                }
            });
            threads[j].start();
        }
        for (int j = 0; j < 10; j++) {
            threads[j].join();
        }
        log.info("多个线程最后的累加操作为，加锁保证线程内部操作的原子性:{}", i);
    }

    /**
     * 加锁保证原子性
     * lock
     */
    @Test
    @SneakyThrows
    public void testVolatileLock() {
        Lock lock = new ReentrantLock();
        Thread[] threads = new Thread[10];
        for (int j = 0; j < 10; j++) {
            threads[j] = new Thread(() -> {
                lock.lock();
                for (int k = 0; k < 10000; k++) {
                    i++;
                }
                lock.unlock();
            });
            threads[j].start();
        }
        for (int j = 0; j < 10; j++) {
            threads[j].join();
        }
        log.info("多个线程最后的累加操作为，加锁保证线程内部操作的原子性:{}", i);
    }


}
