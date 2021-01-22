package com.albert.concurrentpractice.expand.lock;

import com.albert.concurrentpractice.expand.lock.spinlock.SpinLock;
import com.albert.concurrentpractice.expand.lock.spinlock.SpinLockFactory;
import lombok.SneakyThrows;

/**
 * 公平锁和非公平锁的练习
 * 用自旋锁来实现
 *
 * @author Albert
 * @date 2021/1/22 上午11:52
 */
public class FairSpinLockPractice extends Thread {

    /**
     * 实现公平锁
     */
    private static SpinLock lock = SpinLockFactory.getFairSpinLock(true);

    /**
     * 线程顺序
     */
    private int order;
    /**
     * 线程休眠时间
     */
    private long sleepTime;

    public FairSpinLockPractice(int order, long sleepTime) {
        this.order = order;
        this.sleepTime = sleepTime;
    }

    @SneakyThrows
    @Override
    public void run() {
        lock.lock();
        Thread.sleep(sleepTime);
        lock.unlock();
        System.out.println(order+" end");
    }

    @SneakyThrows
    public static void main(String[] args) {
        FairSpinLockPractice test = new FairSpinLockPractice(1, 5000);
        test.start();
        Thread.sleep(500);
        FairSpinLockPractice two = new FairSpinLockPractice(2, 100);
        Thread.sleep(500);
        FairSpinLockPractice three = new FairSpinLockPractice(3, 100);
        Thread.sleep(500);
        FairSpinLockPractice four = new FairSpinLockPractice(4, 100);

        two.start();
        three.start();
        four.start();


        four.join();
    }


}
