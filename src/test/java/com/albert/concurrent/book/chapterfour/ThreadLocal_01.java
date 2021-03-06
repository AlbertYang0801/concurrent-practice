package com.albert.concurrent.book.chapterfour;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal的使用练习
 * @author Albert
 * @date 2021/2/23 下午12:03
 */
public class ThreadLocal_01 {

    //时间格式化
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //使用ThreadLocal封装，解决相同变量访问冲突的问题
    static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();

    /**
     * 多线程操作变量-不安全
     */
    public static class ParseDate implements Runnable {

        private int i;

        public ParseDate(int i) {
            this.i = i;
        }

        @SneakyThrows
        @Override
        public void run() {
            Date date = simpleDateFormat.parse("2020-10-01 16:00:" + i);
            System.out.println(i+" "+date);
        }
    }

    /**
     * 多线程操作变量-安全
     */
    public static class SafeParseDate implements Runnable {

        private int i;

        public SafeParseDate(int i) {
            this.i = i;
        }

        @SneakyThrows
        @Override
        public void run() {
            if (threadLocal.get() == null) {
                threadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            }
            //从ThreadLocal里获取变量
            Date date = threadLocal.get().parse("2020-10-01 16:00:" + i);
            System.out.println(i+" "+date);
        }
    }

    /**
     * 是否线程安全
     */
    public static Runnable isSafe(int i,boolean safeStatus){
        return safeStatus?new SafeParseDate(i):new ParseDate(i);
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 20; i++) {
            executorService.submit(isSafe(i,true));
        }
    }


}
