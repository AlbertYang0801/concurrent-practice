package com.albert.concurrentpractice.expand.countdownlatch;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 工人类
 * @author Albert
 * @date 2020/7/26 00:10
 */
@Slf4j
public class Worker implements Runnable {

    private CountDownLatch countDownLatch;
    private String workerName;

    public Worker(CountDownLatch countDownLatch, String workerName) {
        this.countDownLatch = countDownLatch;
        this.workerName = workerName;
    }

    @Override
    public void run() {
        //开始工作
        this.doWorker();
        //计时器倒数，即计数器减1
        this.countDownLatch.countDown();
    }

    private void doWorker(){
        log.info(this.workerName+"工人开始工作");
        //当前线程休眠5秒, 即工人工作5秒
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(this.workerName+"工人工作结束！");
    }


}
