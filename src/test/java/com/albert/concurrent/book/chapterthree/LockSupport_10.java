package com.albert.concurrent.book.chapterthree;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport的练习
 * 推荐使用的阻塞类，类似于信号量的机制，不会占有锁对象
 * @author Albert
 * @date 2021/1/18 上午10:25
 */
public class LockSupport_10 {

    private static final Object OBJECT = new Object();

    public static class ChangeObject extends Thread {

        private String name;

        public ChangeObject(String name){
            this.name=name;
        }

        @Override
        public void run() {
            synchronized (OBJECT){
                System.out.println(name+" job start");
                //阻塞当前线程
                LockSupport.park();
            }
        }
    }

    public static void main(String[] args) {
        ChangeObject one = new ChangeObject("one");
        ChangeObject two = new ChangeObject("two");

        one.start();
        two.start();

        //结束阻塞
        LockSupport.unpark(one);
        LockSupport.unpark(two);

        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
