package com.albert.concurrent.book.chapterthree;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 限时等待锁的练习-指定等待时间
 * 限时等待锁也可以有效解决死锁的问题
 *
 * @author Albert
 * @date 2021/1/11 下午4:16
 */
public class LockTime_03 implements Runnable{

    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            //在5s内若获取到锁对象，则返回true。若超过5s还没获取锁对象，则返回false
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + ":GET Lock success");
                Thread.sleep(6000);
            } else {
                System.out.println(Thread.currentThread().getName() + ":GET Lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LockTime_03 lockTime = new LockTime_03();
        //第一个线程能获取到锁对象，然后休眠
        Thread threadOne = new Thread(lockTime);
        //第二个线程由于第一个线程持有锁对象休眠了，无法获取锁对象。
        Thread threadTwo = new Thread(lockTime);
        threadOne.start();
        threadTwo.start();
    }


}

