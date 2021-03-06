package com.albert.concurrent.expand.threadlocal;

/**
 * ThreadLocal的简单练习
 * @author Albert
 * @date 2021/3/6 下午5:08
 */
public class ThreadLocalDemo {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        //主线程
        threadLocal.set("main");
        new Thread(() -> {
            //新线程
            threadLocal.set("thread");
            System.out.println(threadLocal.get());
        }).start();
        System.out.println(threadLocal.get());
    }


}
