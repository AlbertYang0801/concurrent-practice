package com.albert.concurrent.threadpool;

import com.albert.concurrent.TestApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.*;

/**
 * 测试线程池关闭方法
 *
 * @author yangjunwei
 * @date 2021/7/8 11:00 上午
 */
@Slf4j
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class CloseTest {

    @Test
    @SneakyThrows
    public void testShutdown() {
        int size = 100;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 1000L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(size));
        CountDownLatch countDownLatch = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {
            threadPoolExecutor.execute(new TestTask(countDownLatch));
        }
        System.out.println(threadPoolExecutor);
        threadPoolExecutor.shutdown();
        System.out.println("执行结束！");
        countDownLatch.await();
        System.out.println(threadPoolExecutor);
        System.out.println("Game over！");
    }

    @Test
    public void testFuhao() {

        class Test {
            boolean test1() {
                System.out.println("111");
                return false;
            }
            boolean test2(){
                System.out.println("222");
                return true;
            }
        }

        Test test = new Test();
        if(test.test2()||test.test1()){
            System.out.println(333);
        }


    }


    @Test
    @SneakyThrows
    public void testShutdownNow() {
        int size = 100;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 1000L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(size));
        CountDownLatch countDownLatch = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {
            threadPoolExecutor.execute(new TestTask(countDownLatch));
        }
        System.out.println(threadPoolExecutor);

        //直接关闭线程池
        threadPoolExecutor.shutdownNow();
        System.out.println("执行结束！");

        countDownLatch.await();
        System.out.println(threadPoolExecutor);
        System.out.println("Game over！");
    }

    @Test
    @SneakyThrows
    public void testClearQueue() {
        int size = 1000;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 1000L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(size));
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < size; i++) {
            threadPoolExecutor.execute(new TestTask(countDownLatch, i));
        }

        Thread.sleep(3000L);
        BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
        TestTask testTask = (TestTask) queue.take();
        Integer num = testTask.getNum();
        System.out.println("执行到了第" + num);
        queue.clear();
        System.out.println("清空队列");
        System.out.println(threadPoolExecutor);
        threadPoolExecutor.shutdown();


        System.out.println("Game over！");
        Thread.sleep(10000L);
        System.out.println(threadPoolExecutor);

    }


    public class TestTask implements Runnable {

        private CountDownLatch countDownLatch;

        private Integer num;

        public TestTask(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        public TestTask(CountDownLatch countDownLatch, Integer num) {
            this.countDownLatch = countDownLatch;
            this.num = num;
        }

        public CountDownLatch getCountDownLatch() {
            return countDownLatch;
        }

        public void setCountDownLatch(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(new Date());
            countDownLatch.countDown();
        }
    }


}
