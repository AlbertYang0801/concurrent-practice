package com.albert.concurrentpractice.book.chapterthree;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 限流RateLimiter的使用
 * 基于令牌桶算法
 * @author Albert
 * @date 2021/1/18 上午10:41
 */
public class RateLimiter_11 {

    /**
     * 限流（限时每秒只能处理2个请求）
     */
    static RateLimiter rateLimiter=RateLimiter.create(2);

    public static class Task implements Runnable{
        @Override
        public void run() {
            System.out.println("任务执行"+System.currentTimeMillis());
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<50;i++){
            //请求获取许可，若获取不到，则等待（执行所有任务）
//            rateLimiter.acquire();

            //尝试获取许可，获取成功返回true，不成功返回false（执行第一个任务）
            if(!rateLimiter.tryAcquire()){
                continue;
            }
            new Thread(new Task()).start();
        }
    }


}


/**
 * 限流，每秒钟允许两个请求，每500ms会生成一个令牌，所以每500ms会进行一个请求。
 * acquire()请求获取许可，获取不到会等待。
 *
 * tryAcquire()方法尝试获取许可，获取成功返回true，不成功返回false。
 * 由于第一个500ms时，只生成了一个令牌，所以会有一个任务执行。
 * 由于循环执行效率高，在500ms内就执行完了，所以只有一个任务被执行。
 */