package com.albert.concurrent.expand.pattern.producermodel;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者
 *
 * @author yangjunwei
 * @date 2021/9/27 7:41 下午
 */
public class ProducerM implements Runnable {

    private volatile boolean isRunning = true;

    //内存缓冲区
    private BlockingQueue<PCData> queue;

    //总数
    private static AtomicInteger count = new AtomicInteger();

    private static final int SLEEPTIME = 1000;

    public ProducerM(BlockingQueue<PCData> queue){
        this.queue=queue;
    }

    @Override
    public void run() {
        PCData pcData = null;
        Random random = new Random();

        System.out.println("start producer id:" + Thread.currentThread().getId());

        try {
            //开启标识
            while (isRunning) {
                Thread.sleep(random.nextInt(SLEEPTIME));
                //构造任务数据
                pcData = new PCData(count.incrementAndGet());
                System.out.println("data put queue : "+pcData.toString());
                //延时2s向队列放数据
                if(!queue.offer(pcData,2, TimeUnit.SECONDS)){
                    System.out.println("put data to queue faied："+pcData.toString());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

    }

    public void stop() {
        isRunning = false;
    }


}
