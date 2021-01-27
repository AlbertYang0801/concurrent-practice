package com.albert.concurrent.book.chaptertwo;

import com.albert.concurrent.TestApplication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Thread的线程退出方法练习
 * 通过为线程添加标记变量的方法，来避免使用stop()方法，保证加锁之后的原子性
 * <p>
 *
 * @author Albert
 * @date 2020/8/15 16:38
 */
@Slf4j
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class ThreadStopResolve_02 {

    private static User user = new User();

    //内部类
    @Data
    @Builder
    @AllArgsConstructor
    public static class User {
        private int id;
        private String name;

        public User() {
            this.id = 0;
            this.name = "0";
        }
    }

    /**
     * 对全局静态对象赋值
     */
    public static class ChangeUser implements Runnable {

        private static boolean stopFlag = false;

        //在需要关闭的线程（写对象操作线程）创建关闭方法
        private void stopMe() {
            stopFlag = true;
        }

        @Override
        public void run() {
            while (true) {
                //退出当前线程
                if(stopFlag){
                    System.out.println("exit ChangeUser Thread");
                    break;
                }
                //持有user锁对象
                synchronized (user) {
                    int id = (int) (System.currentTimeMillis() / 1000);
                    user.setId(id);
                    try {
                        //写对象线程休眠，实现当前线程未全部执行，被终止（调用stop()方法）
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    user.setName(String.valueOf(id));
                }
                //当前线程让出资源
                Thread.yield();
            }
        }
    }

    /**
     * 读取全局的静态对象
     */
    public static class ReadUser implements Runnable {

        @Override
        public void run() {
            while (true) {
                //持有user锁对象
                synchronized (user) {
                    if (user.getId() != Integer.parseInt(user.getName())) {
                        System.out.println(user.toString());
                    }
                }
                //让出线程资源
                Thread.yield();
            }
        }
    }

    @SneakyThrows
    @Test
    public void testStop() {
        //开启一个读取user的线程，一直读取，直到获取到id和name不一样的数据
        new Thread(new ReadUser()).start();

        while (true) {
            //循环开启线程，进行写对象操作
            ChangeUser changeUser = new ChangeUser();
            Thread thread = new Thread(changeUser);
            thread.start();
            Thread.sleep(150);
            //销毁写对象操作的线程(调用线程的线程退出方法)
            changeUser.stopMe();
        }
    }


}

/**
 * Thread的stop()方法：
 * 会直接终止线程，并将该线程拥有的锁对象直接释放（在本例子中对应全局对象user)，其他线程获取该锁对象之后进行写操作，
 * 便可能会造成错误数据的产生（在本例子中会产生id和name不一样的数据）。
 *
 * 实现线程退出：
 * 方法内部通过定义标记变量 stopFLag来控制线程的退出（线程没有业务逻辑处理，会在一定时间后自动销毁）
 */
