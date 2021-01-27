package com.albert.concurrent.book.chapterthree;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅栏并发控制工具CyclicBarrier的练习
 *
 * @author Albert
 * @date 2021/1/13 下午4:20
 */
public class CyclicBarrier_09 {

    /**
     * 士兵
     */
    public static class Soldier implements Runnable {

        private final CyclicBarrier cyclicBarrier;
        private final String soldierName;

        public Soldier(CyclicBarrier cyclicBarrier, String soldierName) {
            this.cyclicBarrier = cyclicBarrier;
            this.soldierName = soldierName;
        }

        @Override
        public void run() {
            try {
                //等待所有士兵到齐
                cyclicBarrier.await();
                dowork();
                //等待所有士兵完成工作
                cyclicBarrier.await();
                System.out.println(soldierName + "     : 集合报数！");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        private void dowork() throws InterruptedException {
            System.out.println(soldierName + "：工作完成");
            Thread.sleep(1000);
        }
    }

    /**
     * 长官
     * 在CyclicBarrier一次计数完成之后，执行一次该方法。（在创建CyclicBarrier时指定）
     */
    public static class Commander implements Runnable {

        private boolean finishFlag = false;
        private int num = 0;

        public Commander(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            if (finishFlag) {
                System.out.println(num + " 个士兵任务完成!");
            } else {
                System.out.println(num + " 个士兵集合完毕!");
                finishFlag = true;
            }
        }


    }

    public static void main(String[] args) {
        final int num = 10;
        Thread[] threads = new Thread[num];
        //创建一个循环栅栏，一次计数器计数完成后，会执行一次指定的方法
        CyclicBarrier cyclicBarrier = new CyclicBarrier(num, new Commander(num));
//        CyclicBarrier cyclicBarrier = new CyclicBarrier(num);

        System.out.println("开始集合");
        for (int i = 0; i < num; i++) {
            //如果等待的线程中被中断，其它等待线程会报BrokenBarrierException异常。
//            if(i==5){
//                threads[0].interrupt();
//            }
            threads[i] = new Thread(new Soldier(cyclicBarrier, i + "士兵"));
            threads[i].start();
        }

    }


}

/**
 * CyclicBarrier循环栅栏：
 * 可指定线程个数，和计数完成后执行的方法。
 * 每个线程内调用await()方法，意为到达栅栏，等到达个数等于指定的线程个数时，开始往下执行（若指定有计数完成后需执行的任务，则先执行该任务）。
 */
