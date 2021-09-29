package com.albert.concurrent.expand.pattern.producermodel;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * @author yangjunwei
 * @date 2021/9/27 7:41 下午
 */
public class ConsumerM implements Runnable {

    //内存缓冲区
    private BlockingQueue<PCData> queue;

    private volatile boolean isRunning = true;

    private static final int SLEEPTIME = 1000;

    public ConsumerM(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("start Consumer id：" + Thread.currentThread().getId());
        Random random = new Random();
        try {
            //无限消费
            while (isRunning) {
                //从队列取数据
                PCData data = queue.take();
                if (Objects.isNull(data)) {
                    int value = data.getIntData() * data.getIntData();
                    System.out.println(MessageFormat.format("{0}*{1}={2}",
                            data.getIntData(),
                            data.getIntData(),
                            value));
                }
                Thread.sleep(random.nextInt(SLEEPTIME));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        isRunning = false;
    }


}
