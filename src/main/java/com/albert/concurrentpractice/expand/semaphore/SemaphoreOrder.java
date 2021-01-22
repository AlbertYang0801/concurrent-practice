package com.albert.concurrentpractice.expand.semaphore;

import java.util.concurrent.Semaphore;

/**
 * 使用信号量Semaphore实现多线程按照顺序执行
 *
 * @author Albert
 * @date 2021/1/12 下午5:14
 */
public class SemaphoreOrder {

    private static Semaphore dev = new Semaphore(0);
    private static Semaphore test = new Semaphore(0);

    public Thread product() {
        return new Thread(() -> {
            System.out.println("产品规划需求");
            //增加dev信号量许可
            dev.release();
        });
    }

    public Thread dev() {
        return new Thread(() -> {
            try {
                //等待product释放dev信号量许可
                dev.acquire();
                System.out.println("开发开发需求");
                //增加test信号量许可
                test.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public Thread test() {
        return new Thread(() -> {
            //等待dev释放test信号量许可
            try {
                test.acquire();
                System.out.println("测试测试需求");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        SemaphoreOrder semaphoreOrder = new SemaphoreOrder();
        System.out.println("早上好");
        System.out.println("测试来上班....");
        semaphoreOrder.test().start();
        System.out.println("产品经理来上班....");
        semaphoreOrder.product().start();
        System.out.println("开发来上班....");
        semaphoreOrder.dev().start();
    }


}
