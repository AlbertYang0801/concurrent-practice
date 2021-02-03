package com.albert.concurrent.lock.deadlock;

import lombok.SneakyThrows;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 创造死锁
 * 使用中断解除死锁
 *
 * @author Albert
 * @date 2021/1/22 上午10:42
 */
public class InterruptDeadLock extends Thread {

    public static ReentrantLock lockOne = new ReentrantLock();
    public static ReentrantLock lockTwo = new ReentrantLock();

    Integer flag;

    public InterruptDeadLock(Integer flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            if (flag == 1) {
                //先获取第一把锁
                lockOne.lockInterruptibly();
                Thread.sleep(500);
                //再获取第二把锁
                lockTwo.lockInterruptibly();
            } else {
                //先获取第二把锁
                lockTwo.lockInterruptibly();
                Thread.sleep(500);
                //再获取第一把锁
                lockOne.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //若持有锁，则释放锁（有效避免异常的出现）
            if (lockOne.isHeldByCurrentThread()) {
                lockOne.unlock();
            }
            if (lockTwo.isHeldByCurrentThread()) {
                lockTwo.unlock();
            }
        }
        System.out.println(flag + "执行完成");
    }

    @SneakyThrows
    public static void main(String[] args) {
        Thread one = new InterruptDeadLock(1);
        Thread two = new InterruptDeadLock(2);
        one.start();
        two.start();
        //使用中断破坏死锁
        one.interrupt();
        one.join();
        two.join();
    }


}
