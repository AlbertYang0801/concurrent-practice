package com.albert.concurrent.synchronizedprac;

import lombok.SneakyThrows;

/**
 * 同步代码块
 * 使用this和对象作为锁对象
 *
 * @author Albert
 * @date 2020/12/29 下午5:10
 */
public class SynchrodizedCodebolck {

    @SneakyThrows
    public static void main(String[] args) {
        SumNumber sumNumber = new SumNumber();
        Thread threadOne = new Thread(sumNumber);
        Thread threadTwo = new Thread(sumNumber);
        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();
        System.out.println(SumNumber.i);
        System.out.println(SumNumber.m);
        System.out.println(SumNumber.n);
        System.out.println(SumNumber.x);
    }


}


/**
 * 同步代码块，持有不同的锁对象
 */
class SumNumber implements Runnable {

    static int i = 0;

    static int m = 0;

    static int n = 0;

    static int x = 0;

    static final Object OBJECT = new Object();

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {
            //this的锁对象指当前类的实例
            synchronized (this) {
                i++;
            }

            //错误使用对象，新对象作为锁，不是同一个对象锁，无法实现同步
            synchronized (new Object()) {
                m++;
            }

            //正确使用对象作为锁
            synchronized (OBJECT) {
                n++;
            }

            //使用当前类作为锁对象
            synchronized (SynchrodizedCodebolck.class) {
                x++;
            }

        }
    }


}