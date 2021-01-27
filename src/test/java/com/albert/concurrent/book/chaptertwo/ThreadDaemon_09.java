package com.albert.concurrent.book.chaptertwo;

import com.albert.concurrent.TestApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 守护线程的练习
 *
 * @author Albert
 * @date 2020/8/31 14:24
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class ThreadDaemon_09 {

    /**
     * 测试守护线程
     * 守护线程会在所有的用户线程执行完毕之后，结束执行
     */
    @SneakyThrows
    @Test
    public void testDaemonThread() {
        //守护线程
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                System.out.println(Thread.currentThread().getName()+" "+i);
            }
        });
        //用户线程
        Thread workThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName()+" "+i);
            }
        });
        //在start()方法之前设置守护线程，不然会报中断异常
        thread.setDaemon(true);
        thread.start();
        workThread.start();
    }


}
