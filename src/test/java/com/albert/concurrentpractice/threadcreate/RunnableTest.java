package com.albert.concurrentpractice.threadcreate;

import com.albert.concurrentpractice.TestApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程创建的方式：第二种
 * implements Runnable
 * 可以作为Thread类的参数创建线程，也可以与线程池捆绑使用
 *
 * @author Albert
 * @date 2020/8/14 10:56
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class RunnableTest {

    /**
     * 测试创建线程
     */
    @Test
    public void testImplRunnable() {
        RunnableImpl runnableImpl = new RunnableImpl();
        //普通的方法调用
        runnableImpl.run();

        //Runnable作为参数，传入Thread类，创建线程
        Thread thread = new Thread(runnableImpl);
        thread.start();

        log.info("这是主线程");
    }

    /**
     * 使用lambda表达式，传入的是Runnable接口
     */
    @Test
    public void testLambda() {
        new Thread(() -> {
            log.info("lambad表达式创建的线程");
            System.out.println("增加线程内部逻辑");
        }).start();

        log.info("这是主线程，异步执行，比新线程执行快");
    }

    /**
     * 测试Runnable和线程池一起使用
     */
    @Test
    public void testExecutor() {
        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
        RunnableImpl runnable = new RunnableImpl();
        //线程池提交线程
        executorService.submit(runnable);
    }

    /**
     * 测试提交多个线程
     */
    @SneakyThrows
    @Test
    public void testExecutorAllRunnable() {
        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        RunnableImpl runnable = new RunnableImpl();
        //通过原型模式克隆多个线程
        RunnableImpl newRunnable = (RunnableImpl) runnable.clone();
        executorService.submit(runnable);
        executorService.submit(newRunnable);
    }


}

/**
 * Runnable函数式接口的子类对象
 */
@Slf4j
class RunnableImpl implements Runnable, Cloneable {
    @Override
    public void run() {
        log.info("这是实现了Runnable的run()方法的新线程");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
