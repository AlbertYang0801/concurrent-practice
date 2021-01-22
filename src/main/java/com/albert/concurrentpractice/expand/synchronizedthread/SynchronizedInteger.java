package com.albert.concurrentpractice.expand.synchronizedthread;

import lombok.SneakyThrows;

/**
 * @author Albert
 * @date 2021/1/8 上午10:59
 */
public class SynchronizedInteger implements Runnable {

    public static Integer i = 0;

    static SynchronizedInteger synchronizedInteger = new SynchronizedInteger();


    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {
            synchronized (synchronizedInteger){
                i++;
            }
            //i是动态变化的，不能作为锁对象
            synchronized (i){
                i++;
            }
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        Thread threadone = new Thread(new SynchronizedInteger());
        Thread threadtwo = new Thread(new SynchronizedInteger());

        threadone.start();
        threadtwo.start();
        threadone.join();
        threadtwo.join();
        System.out.println(SynchronizedInteger.i);
    }


}


