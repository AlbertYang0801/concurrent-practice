package com.albert.concurrent.lock.spinlock;

import lombok.SneakyThrows;

import java.util.concurrent.ArrayBlockingQueue;
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
    private static BlockingQueue<Thread> blockingQueue = new ArrayBlockingQueue<Thread>(10);

    @SneakyThrows
    @Override
    public void lock() {
        Thread thread = Thread.currentThread();
        blockingQueue.add(thread);
        //自旋
        while (true){
            //若变量为null，则代表锁未被持有，将队头元素设置未引用变量。
            if(atomicReference.compareAndSet(null,blockingQueue.poll())){
                //若队列不包含当前线程对象，则说明，当前引用对象为当前线程，跳出自旋，获取锁资源。
                if(!blockingQueue.contains(thread)){
                    break;
                }
            }
        }
    }

    @Override
    public void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
    }


}


