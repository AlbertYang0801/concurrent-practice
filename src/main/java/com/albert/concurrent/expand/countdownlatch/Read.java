package com.albert.concurrent.expand.countdownlatch;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * 读取操作，在写操作完成后进行
 * @author Albert
 * @date 2020/7/26 01:50
 */
@Slf4j
public class Read implements Runnable{

    private CountDownLatch countDownLatch;

    public Read(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @SneakyThrows
    @Override
    public void run() {
        log.info("用户准备读取文件");
        this.countDownLatch.await();
        log.info("用户读取文件成功！");
    }

}
