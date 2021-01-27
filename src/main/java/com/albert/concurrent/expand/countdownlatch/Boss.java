package com.albert.concurrent.expand.countdownlatch;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * 老板类
 * @author Albert
 * @date 2020/7/26 00:10
 */
@Slf4j
public class Boss implements Runnable{

    private CountDownLatch countDownLatch;

    public Boss(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        log.info("老板等待所有工人完成工作，准备视察工作");
        try {
            //线程休眠，等到计数器countDownLatch为0时唤醒线程，继续执行
            this.countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("工人工作都做完了，老板开始视察工作！");
    }


}
