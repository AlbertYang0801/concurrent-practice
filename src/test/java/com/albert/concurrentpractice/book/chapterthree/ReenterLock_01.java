package com.albert.concurrentpractice.book.chapterthree;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁的练习
 *
 * @author Albert
 * @date 2021/1/11 下午2:21
 */
public class ReenterLock_01 implements Runnable {

    /**
     * 锁对象
     */
    public static ReentrantLock lock = new ReentrantLock();

    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {
            //重入锁，可多次获得（释放要和获取的次数一致）
            lock.lock();
            lock.lock();
            try {
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReenterLock_01 runnable = new ReenterLock_01();
        Thread one = new Thread(runnable);
        Thread two = new Thread(runnable);
        one.start();
        two.start();
        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(i);
    }

}


/**
 * 重入锁：可多次获取锁对象，但是释放锁的次数要和获取锁的次数保持一致。
 * 1.若获取锁对象，比释放的次数多。则当前线程会一直持有锁对象而不释放，其他线程会因为拿不到锁对象而无法进入临界区。
 * 2.若释放锁的次数比获取锁对象的次数多，则会产生IllegalMonitorStateException异常。
 */