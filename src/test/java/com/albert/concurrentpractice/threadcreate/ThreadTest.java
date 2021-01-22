package com.albert.concurrentpractice.threadcreate;

import com.albert.concurrentpractice.TestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 线程创建的方式：第一种
 * extends Thread
 * @author Albert
 * @date 2020/8/14 10:22
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class ThreadTest {

    /**
     * 测试类继承Thread创建线程
     * 测试启动线程 run()和start()的区别
     */
    @Test
    public void testExtendsThread(){

        ThreadExtendsThread runThread = new ThreadExtendsThread();
        //直接调用run()方法，在main线程执行(相当于直接调用该类的方法，没有开启新线程)
        runThread.run();

        ThreadExtendsThread startThread = new ThreadExtendsThread();
        //调用start()方法，才开启了一个新线程和主线程争抢资源，源码实际是调用了操作系统的方法，创建了一个新线程
        startThread.start();

        log.info("这是主线程，异步执行，比新线程方法执行的快");

    }

    /**
     * 匿名内部类创建线程
     */
    @Test
    public void testInnerClassCreate(){
        new Thread(){
            @Override
            public void run() {
                log.info("这是匿名内部类创建的新线程");
            }
        }.start();

        log.info("这是主线程，异步执行，比新线程执行快");
    }


}

@Slf4j
class ThreadExtendsThread extends Thread{
    @Override
    public void run() {
        log.info("这是继承了Thread的run()方法的新线程");
    }
}
