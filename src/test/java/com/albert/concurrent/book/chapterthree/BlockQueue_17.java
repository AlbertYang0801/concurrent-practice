package com.albert.concurrent.book.chapterthree;

import lombok.SneakyThrows;

import java.util.concurrent.*;

/**
 * 阻塞队列的练习
 * 分析源码
 *
 * @author Albert
 * @date 2021/1/19 上午11:13
 */
public class BlockQueue_17 {

    //增加元素的方法
    @SneakyThrows
    public static void add(BlockingQueue<Integer> blockingQueue)  {

        //put()方法，若队列已满，则会进行等待（若有元素出队，则会唤醒该线程，类似于自旋锁的等待方式）
        blockingQueue.put(1);
        blockingQueue.put(2);
        blockingQueue.put(3);
        blockingQueue.put(4);
        blockingQueue.put(5);

        //若队列已满，会直接返回false
        boolean offer = blockingQueue.offer(6);
        System.out.println("offer:" + offer);
        //实际调用了offer()方法，若队列已满，会抛出(queue full)异常
        try {
            boolean add = blockingQueue.add(7);
            System.out.println("add" + add);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static void out(BlockingQueue<Integer> blockingQueue) {

        //出队，若队列为空，则会进行等待（等待元素入队，则会唤醒该线程，类似于自旋锁的等待方式）
        Integer take = blockingQueue.take();
        System.out.println("take:"+take);
        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();

        //出队，若队列为空，返回null
        Integer poll = blockingQueue.poll();
        System.out.println(poll);

        //出队，若队列为空，则在有限时间等待
        Integer timePoll = blockingQueue.poll(3, TimeUnit.SECONDS);
        System.out.println(timePoll);

    }

    /**
     * 有界队列的练习
     */
    public static void arrayBlockQueue(BlockingQueue<Integer> blockingQueue) {

        //测试入队方法
        BlockQueue_17.add(blockingQueue);

        System.out.println("-------开始遍历");
        blockingQueue.forEach(queue -> {
            System.out.println(queue.intValue());
        });
        System.out.println("-------结束遍历");

        //测试出队方法
        BlockQueue_17.out(blockingQueue);
    }

    @SneakyThrows
    public static void main(String[] args) {
        //底层是数组，适合做有界队列
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(5);
        //测试有界队列
        BlockQueue_17.arrayBlockQueue(blockingQueue);

        System.out.println("-----------------------------------");

        LinkedBlockingQueue<Integer> linkedBlockingDeque = new LinkedBlockingQueue<>();
        //测试无界队列
        BlockQueue_17.arrayBlockQueue(linkedBlockingDeque);
    }


}

/**
 * add()方法，实际调用了offer()方法，增加了（Queue Full）的异常信息返回。
 * offer()方法，如果队列已满，无法存放，直接返回false。
 * put()方法，若队列已满，会进行线程等待，直到队列有空余位置，会将线程唤醒，进行插入操作。
 * <p>
 * poll()方法，若队列为空，则返回null.
 * take()方法，若队列为空，会进行线程等待，直到队列不为空，会将等待线程唤醒，进行获取操作。
 * <p>
 * 主要分析put()和take()方法，研究线程等待和线程唤醒的代码。
 */
