package com.albert.concurrent.lock.reentrant;

import com.albert.concurrent.lock.spinlock.SpinLock;
import com.albert.concurrent.lock.spinlock.SpinLockFactory;

/**
 * 可重入锁和不可重入锁的练习
 * 基于自旋锁实现可重入锁和不可重入锁
 *
 * @author Albert
 * @date 2021/1/12 上午10:50
 */
public class SpinLockPractice implements Runnable {

    private static int i = 0;

    /**
     * 控制锁对象是否可重入
     */
    private static SpinLock spinLock = SpinLockFactory.getSpinLock(true);

    private void methodA() throws InterruptedException {
        spinLock.lock();
        System.out.println("加第一把锁");
        //在同一个线程中第二次获取锁对象
        methodB();
        spinLock.unlock();
    }

    private void methodB() throws InterruptedException {
        spinLock.lock();
        System.out.println("加第二把锁");
        for (int j = 0; j < 100000; j++) {
            i++;
        }
        System.out.println("job done");
        Thread.sleep(3000);
        spinLock.unlock();
    }

    @Override
    public void run() {
        try {
            methodA();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SpinLockPractice spinLockPractice = new SpinLockPractice();
        Thread one = new Thread(spinLockPractice);
        Thread two = new Thread(spinLockPractice);
        one.start();
        two.start();
        one.join();
        two.join();
        System.out.println(SpinLockPractice.i);
    }


}

/**
 * 可重入锁：允许同一个线程多次获取同一个锁。
 * 不可重入锁：同一个线程只能获取一次锁对象。
 */