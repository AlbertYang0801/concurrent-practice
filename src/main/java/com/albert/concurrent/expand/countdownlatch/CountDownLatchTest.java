package com.albert.concurrent.expand.countdownlatch;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author Albert
 * @date 2020/7/26 01:04
 */
@Slf4j
public class CountDownLatchTest {

    /**
     * 测试老板检查工人工作的例子
     */
    @Test
    public void testBossWatchWorker() {

        //创建自定义线程工厂
        ThreadFactory myThreadFactory = new ThreadFactoryBuilder().setNameFormat("albert-pool-%d").build();

        //使用线程池的构造函数进行创建线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3,10,
                0L,TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>(),
                myThreadFactory,new ThreadPoolExecutor.DiscardPolicy());

        //创建一个为3的计时器
        CountDownLatch countDownLatch = new CountDownLatch(3);

        //创建Boss线程对象
        Boss boss = new Boss(countDownLatch);

        //创建3个工人线程对象
        Worker workerA = new Worker(countDownLatch, "孙圆圆");
        Worker workerB = new Worker(countDownLatch, "艾伯特");
        Worker workerC = new Worker(countDownLatch, "杨依惠");

        //线程添加到线程池中执行
        threadPoolExecutor.execute(workerA);
        threadPoolExecutor.execute(workerB);
        threadPoolExecutor.execute(workerC);
        threadPoolExecutor.execute(boss);

        try {
            //线程休眠，休眠结束关闭线程池
            TimeUnit.SECONDS.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //关闭线程池
        threadPoolExecutor.shutdown();

    }

    /**
     * 测试读和写操作
     * 读操作在写操作完成之后进行
     */
    @SneakyThrows
    @Test
    public void testRead(){
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        //创建计数器
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Write write = new Write(countDownLatch);
        Read read = new Read(countDownLatch);

        executorService.execute(write);
        executorService.execute(read);

        //休眠5秒
        TimeUnit.SECONDS.sleep(new Random().nextInt(5));
        //关闭线程池
        executorService.shutdown();

    }

    @Test
    @SneakyThrows
    public void testAwait(){
        CountDownLatch countDownLatch = new CountDownLatch(10);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i =0;i<10;i++){
            Write write = new Write(countDownLatch);
            executorService.execute(write);
        }
        log.info("任务添加完成，开始等待");
        countDownLatch.await();
        log.info("执行完成!!");
        executorService.shutdown();
    }


}
