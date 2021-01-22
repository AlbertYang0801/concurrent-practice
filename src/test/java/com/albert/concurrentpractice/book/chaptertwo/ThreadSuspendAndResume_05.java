package com.albert.concurrentpractice.book.chaptertwo;

/**
 * 测试线程挂起方法suspend和线程继续执行方法resume
 * 不推荐使用
 * 原因：suspend()方法将线程挂起后，被挂起的线程会继续持有锁
 * @author Albert
 * @date 2021/1/14 下午4:18
 */
public class ThreadSuspendAndResume_05 {

    public final static Object object= new Object();

    public static class ChangeObjectThread implements Runnable {

        private String name;

        public ChangeObjectThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            synchronized (object){
                System.out.println(name + "start!");
                //线程挂起，当前线程会继续持有锁
                Thread.currentThread().suspend();
            }
        }
    }

    public static void main(String[] args) {
        Thread one = new Thread(new ChangeObjectThread("one"));
        Thread two = new Thread(new ChangeObjectThread("two"));
        one.start();
        two.start();
        //线程继续执行
        one.resume();
        two.resume();
        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
