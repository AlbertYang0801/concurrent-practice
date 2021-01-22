package com.albert.concurrentpractice.book.chapterthree;

import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition对象练习（和lock关联的，实现线程等待和唤醒）
 *
 * @author Albert
 * @date 2021/1/11 下午6:16
 */
public class Condition_06 implements Runnable {

    private static ReentrantLock lock = new ReentrantLock();

    private static Condition condition = lock.newCondition();

    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println("await before");
            //线程等待，释放锁对象。线程若是被唤醒，则等待获取锁，获取锁后继续向下执行。
            condition.await();
            System.out.println("thread await end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    @SneakyThrows
    public static void main(String[] args) {
        Condition_06 runnable = new Condition_06();
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(2000);
        System.out.println("开始唤醒线程重新竞争锁对象");
        Condition_06.lock.lock();
        //唤醒一个等待中的线程，继续向下执行。
        Condition_06.condition.signal();
        Condition_06.lock.unlock();
    }


}

