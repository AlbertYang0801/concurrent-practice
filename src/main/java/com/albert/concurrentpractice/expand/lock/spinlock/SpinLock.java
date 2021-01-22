package com.albert.concurrentpractice.expand.lock.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁的抽象类
 *
 * @author Albert
 */
public abstract class SpinLock {

    /**
     * 原子类型引用变量
     */
    public static AtomicReference<Thread> atomicReference = new AtomicReference<>();

    /**
     * 加锁
     */
    public abstract void lock();

    /**
     * 解锁
     */
    public abstract void unlock();


}
