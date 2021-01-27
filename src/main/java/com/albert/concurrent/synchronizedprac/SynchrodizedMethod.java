package com.albert.concurrent.synchronizedprac;

import lombok.SneakyThrows;

/**
 * 同步方法
 * 使用synchrodized关键字实现线程同步
 * 关键点：保证同步代码块的锁对象是一致的
 *
 * @author Albert
 * @date 2020/12/29 下午5:02
 */
public class SynchrodizedMethod {

    /**
     * 01_普通方法同步（没有实现线程同步）
     * 锁对象为实例对象，创建线程时传入两个不同的实例，代表的是两把锁对象，无法对这两个线程类实现线程同步
     *
     */
    @SneakyThrows
    public int numberOperating() {
        Thread threadOne = new Thread(new NumberOperating());
        Thread threadTwo = new Thread(new NumberOperating());
        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();
        return NumberOperating.i;
    }

    /**
     * 02_普通方法同步（实现了线程同步）
     * 使用的是同一个实例对象创建线程
     *
     * @return
     */
    @SneakyThrows
    public int numberOperatingSyn() {
        NumberOperating.i = 0;
        NumberOperating numberOperating = new NumberOperating();
        Thread threadOne = new Thread(numberOperating);
        Thread threadTwo = new Thread(numberOperating);
        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();
        return NumberOperating.i;
    }

    /**
     * 03_静态方法同步，都能够实现线程同步
     *
     * @return
     */
    @SneakyThrows
    public int numberOperatingStatic() {
        NumberOperating.i = 0;
        NumberOperatingStatic numberOperatingStatic = new NumberOperatingStatic();
        Thread threadOne = new Thread(numberOperatingStatic);
        Thread threadTwo = new Thread(new NumberOperatingStatic());
        Thread threadThree = new Thread(new NumberOperatingStatic());

        threadOne.start();
        threadTwo.start();
        threadThree.start();
        threadOne.join();
        threadTwo.join();
        threadThree.join();
        return NumberOperatingStatic.m;
    }

    public static void main(String[] args) {
        SynchrodizedMethod synchrodizedMethod = new SynchrodizedMethod();
        System.out.println("------------------01----------------");
        System.out.println("普通同步方法-传入两个实例对象");
        int i = synchrodizedMethod.numberOperating();
        System.out.println(i);
        System.out.println("没有实现线程同步");


        System.out.println("------------------02----------------");
        System.out.println("普通同步方法-传入相同的实例对象");
        int synI = synchrodizedMethod.numberOperatingSyn();
        System.out.println(synI);
        System.out.println("实现了线程同步");


        System.out.println("------------------03----------------");
        System.out.println("静态同步方法");
        int m = synchrodizedMethod.numberOperatingStatic();
        System.out.println(m);
        System.out.println("实现了线程同步");

    }


}

/**
 * 在普通方法添加synchrodized关键字
 */
class NumberOperating implements Runnable {

    static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {
            //调用普通同步方法
            increaseI();
            increase();
        }
    }

    /**
     * 普通同步方法，锁对象为当前类的实例对象等同于this
     */
    public synchronized void increaseI() {
        i++;
    }

    /**
     * 普通同步方法，锁对象为this，等价于上面的方法
     */
    public void increase() {
        synchronized (this) {
            i++;
        }
    }


}

/**
 * 在静态方法增加synchrodized关键字
 */
class NumberOperatingStatic implements Runnable {

    static int m = 0;

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {
            increaseM();
            increase();
        }
    }

    /**
     * 静态同步方法,锁对象为类对象
     */
    private synchronized static void increaseM() {
        m++;
    }

    /**
     * 静态同步方法,锁对象为类对象,等价于上面的方法
     */
    private static void increase() {
        synchronized (NumberOperatingStatic.class) {
            m++;
        }
    }


}

