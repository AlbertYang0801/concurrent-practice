package com.albert.concurrentpractice.book.chapterthree;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 限时等待锁的练习-不指定等待时间
 *
 * @author Albert
 * @date 2021/1/11 下午4:38
 */
public class LockTime_04 implements Runnable{

    private static ReentrantLock lockOne = new ReentrantLock();
    private static ReentrantLock lockTwo = new ReentrantLock();
    int lock;

    public LockTime_04(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        //先获取锁对象one，再获取锁对象two
        if (lock == 1) {
            while (true) {
                if (lockOne.tryLock()) {
                    try {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (lockTwo.tryLock()) {
                            try {
                                System.out.println(Thread.currentThread().getName() + ":job done!");
                                return;
                            } finally {
                                lockTwo.tryLock();
                            }
                        }
                    } finally {
                        lockOne.unlock();
                    }
                }
            }
        } else {
            //先获取锁对象two，再获取锁对象one
            while (true) {
                if (lockTwo.tryLock()) {
                    try {
                        Thread.sleep(500);
                        if (lockOne.tryLock()) {
                            try {
                                System.out.println(Thread.currentThread().getName() + ":job jone!");
                                return;
                            } finally {
                                lockOne.unlock();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lockTwo.unlock();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        LockTime_04 one = new LockTime_04(1);
        LockTime_04 two = new LockTime_04(2);
        Thread threadOne = new Thread(one);
        Thread threadTwo = new Thread(two);
        threadOne.start();
        threadTwo.start();
    }


}

