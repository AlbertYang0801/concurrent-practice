package com.albert.concurrent.expand.semaphore;

import com.albert.concurrent.threadpool.ThreadPoolCreate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

/**
 * 信号量Semaphore练习
 * 实现停车示例：
 * 停车场只有10个车位，现在有30辆车去停车。当车位满时出来一辆车才能有一辆车进入停车。
 *
 * @author Albert
 * @date 2020/8/2 21:34
 */
public class ParkingCars implements Runnable {

    /**
     * 创建一个许可为10的信号量
     */
    private static Semaphore semaphore = new Semaphore(10);

    private int carNo;

    public ParkingCars(int carNo) {
        this.carNo = carNo;
    }

    @Override
    public void run() {
        try {
            //获取信号量许可，获取车位（若信号量可用许可为0，则线程进入等待，直到有车位释放）
            semaphore.acquire();
            System.out.println(carNo + "号车子开始停车");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放车位
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = ThreadPoolCreate.getCachedThreadPool();
        for (int i = 0; i < 30; i++) {
            ParkingCars runnable = new ParkingCars(i);
            executorService.submit(runnable);
            //获取线程类信号量当前可用许可个数
            System.out.println(ParkingCars.semaphore.availablePermits());
        }
        executorService.shutdown();
    }


}
