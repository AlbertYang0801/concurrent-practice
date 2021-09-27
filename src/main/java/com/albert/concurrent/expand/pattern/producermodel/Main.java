package com.albert.concurrent.expand.pattern.producermodel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author yangjunwei
 * @date 2021/9/27 10:22 下午
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        //建立一个大小为10的缓冲区
        BlockingQueue<PCData> queue=new LinkedBlockingQueue<>(10);
        ProducerM producerM= new ProducerM(queue);
        ProducerM producerM1= new ProducerM(queue);
        ProducerM producerM2= new ProducerM(queue);
        ProducerM producerM3= new ProducerM(queue);
        ConsumerM consumerM = new ConsumerM(queue);
        ConsumerM consumerM1 = new ConsumerM(queue);

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(producerM);
        executorService.execute(producerM1);
        executorService.execute(producerM2);
        executorService.execute(producerM3);
        executorService.execute(consumerM);
        executorService.execute(consumerM1);

        Thread.sleep(10000);
        producerM.stop();
        producerM1.stop();
        producerM2.stop();
        producerM3.stop();
        consumerM.stop();
        consumerM1.stop();
        Thread.sleep(3000);
        executorService.shutdown();
    }


}
