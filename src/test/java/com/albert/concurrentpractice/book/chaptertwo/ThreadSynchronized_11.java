package com.albert.concurrentpractice.book.chaptertwo;

import com.albert.concurrentpractice.TestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 练习synchronized关键字
 * @author Albert
 * @date 2020/12/28 下午4:56
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class ThreadSynchronized_11 {

    /**
     * volatile关键字不能保证原子性
     */
    static volatile int i = 0;

    /**
     * 不加锁不保证原子性
     *
     * @throws InterruptedException
     */
    @Test
    public void unAssureAtomicity() throws InterruptedException {
        Runnable instance = () -> {
            for (int j = 0; j < 100000; j++) {
                i++;
            }
        };
        Thread threadOne = new Thread(instance);
        Thread threadTwo = new Thread(instance);
        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();
        //两个线程同时操作同一个变量，不能保证原子性，实际结果小于理想结果。
        System.out.println(i);
    }

    /**
     * 使用synchroized关键字加锁
     *
     * @throws InterruptedException
     */
    @Test
    public void useSynchroized() throws InterruptedException {
        Runnable instance = () -> {
            //加锁保证线程执行的原子性，this作用的是当前实例对象
            synchronized (this) {
                for (int j = 0; j < 100000; j++) {
                    i++;
                }
            }
        };
        //创建的两个线程指向的是同一个runnable类，能保证锁对象的共享
        Thread threadOne = new Thread(instance);
        Thread threadTwo = new Thread(instance);
        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();
        System.out.println(i);
    }

    /**
     * 根据创建两个线程
     *
     * @throws InterruptedException
     */
    @Test
    public void objectSynchroized() throws InterruptedException {
        AccountAsyn accountAsyn = new AccountAsyn();
        //两个线程创建时，使用的是同一个实例对象，保证普通方法加锁的有效性
        Thread threadOne = new Thread(accountAsyn);
        Thread threadTwo = new Thread(accountAsyn);
        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();
        System.out.println(AccountAsyn.x);
    }

    /**
     * 创建两个线程，使用的不是同一个实例
     *
     * @throws InterruptedException
     */
    @Test
    public void distinctRunnable() throws InterruptedException {
        //两个线程创建时，使用的不是相同实例的线程类，故每个线程都有自己的对象锁，无法实现线程同步。
        Thread threadOne = new Thread(new AccountAsyn());
        Thread threadTwo = new Thread(new AccountAsyn());
        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();
        System.out.println(AccountAsyn.x);
    }


}


/**
 * 线程类
 */
class AccountAsyn implements Runnable {

    static volatile int x = 0;

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {
            additionX();
        }
    }

    //普通方法的锁对象是当前类的实例对象
    private synchronized void additionX() {
        x++;
    }


}