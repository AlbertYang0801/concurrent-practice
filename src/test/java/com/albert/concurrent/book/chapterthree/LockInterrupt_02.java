package com.albert.concurrent.book.chapterthree;

import lombok.SneakyThrows;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁中断特性的练习
 * 中断可以解决多线程之间的死锁问题
 *
 * @author Albert
 * @date 2021/1/11 下午2:50
 */
public class LockInterrupt_02 implements Runnable{

    public static ReentrantLock lockOne = new ReentrantLock();
    public static ReentrantLock lockTwo = new ReentrantLock();

    /**
     * 加锁顺序
     */
    int lock;

    public LockInterrupt_02(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                //先加第一把锁
                lockOne.lockInterruptibly();
                Thread.sleep(500);
                //再加第二把锁
                lockTwo.lockInterruptibly();
            } else {
                //先加第二把锁
                lockTwo.lockInterruptibly();
                Thread.sleep(500);
                //再加第一把锁
                lockOne.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //如果当前线程持有该锁对象，则释放锁
            if (lockOne.isHeldByCurrentThread()) {
                lockOne.unlock();
            }
            if (lockTwo.isHeldByCurrentThread()) {
                lockTwo.unlock();
            }
            System.out.println(Thread.currentThread().getId() + "线程退出");
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        //在两个线程之间创建死锁
        LockInterrupt_02 runnableOne = new LockInterrupt_02(1);
        LockInterrupt_02 runnableTwo = new LockInterrupt_02(2);
        Thread threadOne = new Thread(runnableOne);
        Thread threadTwo = new Thread(runnableTwo);
        threadOne.start();
        threadTwo.start();
        Thread.sleep(1000);
        threadTwo.interrupt();
    }


}


/**
 * 死锁产生的原因：
 * 第一个线程先获取到one锁，再去请求two锁。
 * 第二个线程先获取到two锁，再去请求one锁。
 * 两个线程都在请求对方已持有的锁对象，故产生了死锁。
 * 破坏死锁：将其中一个线程中断，中断之后，会放弃获取锁对象，并释放已持有的锁对象。
 */