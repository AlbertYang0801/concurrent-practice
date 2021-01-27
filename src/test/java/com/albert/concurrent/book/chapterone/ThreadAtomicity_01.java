package com.albert.concurrent.book.chapterone;

import com.albert.concurrent.TestApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 多线程的原子性
 * 例1：对应19页的例子
 *
 * @author Albert
 * @date 2020/8/15 16:38
 */
@Slf4j
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class ThreadAtomicity_01 {

    private static long v = 0;

    public static class ChangeV implements Runnable {

        private long oper;

        public ChangeV(long oper) {
            this.oper = oper;
        }

        @Override
        public void run() {
            while (true) {
                //线程启动赋值全局变量
                ThreadAtomicity_01.v = oper;
                //让出线程资源
                Thread.yield();
            }
        }
    }

    public static class ReadV implements Runnable {
        @Override
        public void run() {
            while (true) {
                //读取全局变量
                long temp = ThreadAtomicity_01.v;
                if (temp != 111L & temp != 222L & temp != 333L) {
                    log.info(String.valueOf(temp));
                    System.out.println(temp);
                }
                Thread.yield();
            }
        }
    }

    @SneakyThrows
    @Test
    public void testAtom() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5,
                10L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1024));
        threadPoolExecutor.execute(new ChangeV(111L));
        threadPoolExecutor.execute(new ChangeV(222L));
        threadPoolExecutor.execute(new ChangeV(333L));
        threadPoolExecutor.execute(new ReadV());
        Thread.sleep(1000);
        threadPoolExecutor.shutdown();
    }



}

/**
 * 多线程的原子性：
 *      是指一个操作是不可中断的。即使是多个线程一起执行的时候，一个操作一旦开始，就不会被其他线程干扰。
 *
 * java中的原子操作：
 * 1.除了long和double类型的赋值操作
 *      在32位长度操作系统中，long和double类型的赋值不是原子操作。
 *      因为long和double都是64位的，在32位系统上，对long和double类型的数据进行读写都要分为两步完成，
 *      若同时两个线程同时写一个变量内存，一个写低8位，一个写高8位，就会导致无效数据出现。
 *      解决办法：long和double类型声明为volatile。
 *          java的内存模型保证声明为volatile的long和double变量的get和set操作是原子的。
 * 2.所有引用reference的赋值操作（待研究）
 * 3.java.concurrent.Atomic.* 包中所有类的一切操作
 */

/**
 * 测试多线程的原子性：
 *      多个线程同时操作一个变量，另外的线程打印变量，多线程的操作具有原子性
 *      即单个线程操作数据的过程中是不会被其他线程所干扰的。
 *      所以全局变量v的赋值，只会是传入的值，不会出现其他的值。
 */

/**
 * 本例子测试的是long型数据打印不符合多线程的原子性：
 * 预计效果：JVM虚拟机的位数若为32位，而long型是64位的数据类型，在赋值的时候会产生拼接错误（即前32为是一个线程传入的值的，后32位是另一个线程传入的值的）
 *          会打印出传入的值之外的结果。
 * 本例子没有结果打印，说明没有拼接错误的值产生。
 *          原因是因为系统的JVM位数为64位，可满足单个线程赋值long型的操作。
 *          间接证明了多线程的原子性。
 */