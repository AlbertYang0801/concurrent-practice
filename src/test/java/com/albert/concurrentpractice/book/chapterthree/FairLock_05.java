package com.albert.concurrentpractice.book.chapterthree;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁
 * 注意：synchronized关键字实现的同步，锁对象是不公平的。
 * 重入锁可以实现公平锁
 *
 * @author Albert
 * @date 2021/1/11 下午5:30
 */
public class FairLock_05 implements Runnable {

    //公平锁（指定为true，保证每个线程顺序获取锁对象）
    private static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName() + ":GET LOCK");
            } finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLock_05 runnable = new FairLock_05();
        Thread threadOne = new Thread(runnable);
        Thread threadTwo = new Thread(runnable);
        threadOne.start();
        threadTwo.start();
    }


}


/**
 * 公平锁：多个线程会按照顺序执行。
 * 优点：不会造成饥饿。
 * 缺点：需要维护一个有序队列，实现成本高，性能低下。
 * <p>
 * 非公平锁：已经获取锁对象的线程有更大概率继续持有锁对象。
 * 优点：执行效率高。
 * 缺点：容易造成饥饿现象。
 */