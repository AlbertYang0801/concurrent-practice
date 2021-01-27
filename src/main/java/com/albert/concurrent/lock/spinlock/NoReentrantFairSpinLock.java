package com.albert.concurrent.lock.spinlock;

import lombok.SneakyThrows;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 公平锁的实现练习
 * 公平锁、不可重入锁
 * 基于NoReentrantSpinLock不可重入锁（非公平）实现公平锁
 * 原理：维护一个队列
 * @author Albert
 * @date 2021/1/12 上午10:39
 */
public class NoReentrantFairSpinLock extends SpinLock {

    /**
     * 线程队列
     */
    private static BlockingQueue<Thread> blockingQueue = new LinkedBlockingDeque<>();

    @SneakyThrows
    @Override
    public void lock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread);
        blockingQueue.put(thread);
        //当atomicReference为空时，将当前线程赋值给atomicReference（注意：第一个线程进入，while内条件为false，不会进入循环）
        while (!atomicReference.compareAndSet(null, blockingQueue.poll())) {

        }
    }

    @Override
    public void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
    }


}


