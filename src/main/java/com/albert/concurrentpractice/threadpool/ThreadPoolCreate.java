package com.albert.concurrentpractice.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * 线程池的创建方式
 *
 * @author Albert
 * @date 2020/8/11 10:49
 */
@Slf4j
public class ThreadPoolCreate {

    /**
     * 创建一个缓存型的线程池
     */
    public static ExecutorService getCachedThreadPool() {
        //使用默认的工厂
        return Executors.newCachedThreadPool();
    }

    /**
     * 创建一个缓存型的线程池，使用自定义ThreadFactory
     */
    private static ExecutorService getCachedThreadPoolByThreadFactory() {
        //使用自定义的线程工厂
        return Executors.newCachedThreadPool(new ThreadFactoryBuilder().build());
    }

    /**
     * 创建一个定长型的线程池
     *
     * @param nThreads 线程个数
     */
    public static ExecutorService getFixedThreadPool(int nThreads) {
        return Executors.newFixedThreadPool(nThreads);
    }

    /**
     * 创建一个定长型的线程池
     *
     * @param nThreads      线程个数
     * @param threadFactory 自定义线程工厂
     */
    public static ExecutorService getFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
        //使用自定义的线程工厂
        return Executors.newFixedThreadPool(nThreads, threadFactory);
    }

    /**
     * 创建一个单线程的线程池
     */
    public static ExecutorService getSingleThreadExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    /**
     * 创建一个单线程的线程池
     *
     * @param threadFactory 自定义线程工厂
     */
    public static ExecutorService getSingleThreadExecutor(ThreadFactory threadFactory) {
        return Executors.newSingleThreadExecutor(threadFactory);
    }

    /**
     * 创建一个定时的线程池
     *
     */
    public static ScheduledExecutorService getScheduledThreadPool(int corePoolSize) {
        return Executors.newScheduledThreadPool(corePoolSize);
    }

    /**
     * 阿里规约推荐的手动创建线程池
     *
     * @param corePoolSize
     * @param maximumPoolSize
     * @return
     */
    public static ExecutorService getThreadPoolByAlibaba(int corePoolSize, int maximumPoolSize) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("albert-pool-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        return pool;
    }

    /**
     * 阿里规约推荐的创建定时线程池的方式
     */
    public static ExecutorService getScheduledThreadPoolByAliBaba() {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder()
                        .namingPattern("albert-schedule-pool-%d")
                        .daemon(true)
                        .build());
        return scheduledExecutorService;
    }

    /**
     * 创建一个抢占式的线程池
     * 1.8新增的线程池类型
     *
     */
    public static ExecutorService newWorkStealingPool() {
        ExecutorService executorService = Executors.newWorkStealingPool();
        return executorService;
    }

    /**
     * 创建一个指定并行线程数的抢占式线程池
     * 1.8新增的线程池类型
     *
     */
    public static ExecutorService newWorkStealingPool(int parallelism) {
        //指定最大并行线程数
        ExecutorService executorService = Executors.newWorkStealingPool(parallelism);
        return executorService;
    }

    /**
     * 调用线程池的构造方法创建线程池
     *
     * @param corePoolSize    最小线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime   线程空闲的销毁时间
     * @param unit            销毁时间的单位
     * @param workQueue       任务队列
     * @param threadFactory   线程工厂
     * @param handler         任务拒绝策略
     * @return
     */
    public static ExecutorService getThreadPoolByParam(int corePoolSize,
                                                       int maximumPoolSize,
                                                       long keepAliveTime,
                                                       TimeUnit unit,
                                                       BlockingQueue<Runnable> workQueue,
                                                       ThreadFactory threadFactory,
                                                       RejectedExecutionHandler handler) {
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        return threadPoolExecutor;
    }


}
