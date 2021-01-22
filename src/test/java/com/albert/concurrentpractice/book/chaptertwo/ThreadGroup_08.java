package com.albert.concurrentpractice.book.chaptertwo;

import com.albert.concurrentpractice.TestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 线程组的练习
 *
 * @author Albert
 * @date 2020/8/31 12:00
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class ThreadGroup_08 {

    public static class ThreadGroupName implements Runnable {
        @Override
        public void run() {
            System.out.println("线程的名称为：" + Thread.currentThread().getThreadGroup().getName() + "-" + Thread.currentThread().getName());
        }
    }

    @Test
    public void testThreadGroup() {
        //创建一个线程组
        ThreadGroup threadGroup = new ThreadGroup("thread-group");
        new Thread(threadGroup, new ThreadGroupName(), "Thread-Albert-1").start();
        new Thread(threadGroup, new ThreadGroupName(), "Thread-Albert-2").start();
        //获取线程组内线程数
        System.out.println(threadGroup.activeCount());
        //打印线程组信息，便于查看日志
        threadGroup.list();
    }


}
