package com.albert.concurrentpractice.expand.countdownlatch;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 写操作
 * @author Albert
 * @date 2020/7/26 01:47
 */
@Slf4j
public class Write implements Runnable {

    private final CountDownLatch countDownLatch;

    public Write(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @SneakyThrows
    @Override
    public void run() {
        log.info("用户开始写入");
        //线程休眠，模拟写操作
        TimeUnit.SECONDS.sleep(new Random().nextInt(2));
        log.info("用户写入完成");
        this.countDownLatch.countDown();
    }


}
