package com.albert.concurrent.book.chapterthree;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁的练习
 *
 * @author Albert
 * @date 2021/1/13 上午10:57
 */
public class ReadWriteLock_08 {

    /**
     * 读写锁
     */
    private final static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 读锁
     */
    private final static Lock readLock = readWriteLock.readLock();

    /**
     * 写锁
     */
    private final static Lock writeLock = readWriteLock.writeLock();

    /**
     * 可重入锁，用来和读写锁比较效率
     */
    private final static Lock lock = new ReentrantLock();

    private int value;

    /**
     * 模拟读操作
     */
    public int handleRead(Lock lock) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1000);
            //返回读取到的值
            return value;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 模拟写操作
     */
    public void handleWrite(Lock lock, int index) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1000);
            //修改值
            this.value = index;
            System.out.println("修改的值为：" + index);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteLock_08 readWriteLock08 = new ReadWriteLock_08();

        //读线程（与写线程互斥，与读线程可并行）
        Runnable readRunnable = () -> {
            try {
                //读操作（指定锁为读锁）
                int i = readWriteLock08.handleRead(readLock);
                System.out.println("读取到的数值为：" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        //写线程（与其它线程互斥）
        Runnable writeRunnable = () -> {
            try {
                //写操作（指定锁为写锁）
                readWriteLock08.handleWrite(writeLock, new Random().nextInt());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 2; i++) {
            //写线程执行，执行时是互斥的，写线程执行时会独占资源。
            new Thread(writeRunnable).start();
        }

        for (int i = 0; i < 18; i++) {
            //读线程执行，执行时是并发的。遇到写线程执行会阻塞，等待写线程执行完毕释放资源。
            new Thread(readRunnable).start();
        }


    }


}

/**
 * 读写锁：
 * 1.读锁与读锁之间是不互斥的，读锁与写锁之间是互斥的。
 * 2.写锁与其它锁都是互斥的。
 * 保证写锁是独占资源的。
 * 读线程之间是并发执行的，而写线程执行的时候是独占的。能提高读线程的执行效率。
 * <p>
 * 与可重入锁比较：
 * 可重入锁是互斥的。
 * 将读线程和写线程的锁换成可重入锁，之后线程按照顺序执行，执行效率变慢。
 */
