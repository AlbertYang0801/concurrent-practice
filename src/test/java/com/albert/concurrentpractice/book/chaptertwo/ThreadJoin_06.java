package com.albert.concurrentpractice.book.chaptertwo;

import com.albert.concurrentpractice.TestApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 等待线程结束join()方法和礼让线程yeild()方法练习
 *
 * <p>
 *
 * @author Albert
 * @date 2020/8/26 19:16
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class ThreadJoin_06 {

    private static int i=0;

    public static class ChangeIntValue extends Thread{
        @Override
        public void run() {
            for(i=0;i<1000;i++);
            //让出线程资源（让出CPU资源，但是会重新竞争CPU资源）
            Thread.yield();
        }
    }

    /**
     * 测试join()方法
     */
    @SneakyThrows
    @Test
    public  void testJoin() {
        ChangeIntValue changeIntValue = new ChangeIntValue();
        changeIntValue.start();
        //等待线程结束，等待指定线程执行结束
        changeIntValue.join();
        System.out.println(i);
    }


}
