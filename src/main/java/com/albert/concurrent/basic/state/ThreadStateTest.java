package com.albert.concurrent.basic.state;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * 线程状态测试
 *
 * @author yangjunwei
 * @date 2021/10/9 2:00 下午
 */
public class ThreadStateTest {

    /**
     * 测试线程状态枚举
     */
    @Test
    public void testStateEmun() {
        //线程状态枚举类
        Thread.State[] values = Thread.State.values();
        for (Thread.State value : values) {
            String name = value.name();
            System.out.println(name);
        }
    }

    /**
     * 测试NEW
     */
    @Test
    public void testNewState() {
        Thread thread = new Thread(() -> {
        });
        //未运行的线程状态是NEW
        Thread.State state = thread.getState();
        System.out.println(state.name());
    }

    /**
     * 测试RUNNABLE
     */
    @Test
    public void testRunnableState() {
        Thread thread = new Thread(() -> {
        });
        //调用run方法，并不会启动线程，此时运行状态还是NEW
//        thread.run();
        //线程启动后，运行状态为RUNNABLE
        thread.start();
        Thread.State state = thread.getState();
        System.out.println(state.name());
    }

    /**
     * 测试BLOCKED
     */
    @Test
    public void testBlockedState() {
        Object object = new Object();
        Runnable runnable = () -> {
            synchronized (object) {
                while (true) {
                    //线程独占资源，谁先抢到谁运行，未抢到的会阻塞
                }
            }
        };
        Thread threadOne = new Thread(runnable);
        Thread threadTwo = new Thread(runnable);
        //线程one获取资源，状态为：RUNNABLE
        threadOne.start();
        //线程two被阻塞，状态为：BLOCKED
        threadTwo.start();
        System.out.println("threadOne name :" + threadOne.getName() + "; state :" + threadOne.getState().name());
        System.out.println("threadTwo name :" + threadTwo.getName() + "; state :" + threadTwo.getState().name());
    }

    /**
     * 测试WAITING
     * 使用object.wait()
     */
    @SneakyThrows
    @Test
    public void testWaitingState() {
        //对象锁
        Object object = new Object();
        //第一个线程等待
        Thread threadOne = new Thread(() -> {
            try {
                Thread.sleep(1000);
                synchronized (object) {
                    System.out.println("threadOne wait！！！");
                    //threadOne进行等待
                    object.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("threadOne被唤醒之后继续执行！");
            System.out.println("threadOne end！");
        });

        //第二个线程通知
        Thread threadTwo = new Thread(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("threadOne state：" + threadOne.getState().name());
                synchronized (object) {
                    System.out.println("threadTwo notify threadOne!!！");
                    //唤醒threadOne（并不直接释放锁，而是让threadOne开始竞争锁）
                    object.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("threadTwo notify threadOne 后并没有直接退出，而是继续执行!!！");
        });
        threadOne.start();
        threadTwo.start();
        Thread.sleep(10000);
    }

    /**
     * 测试WAITING
     * 使用thread.join()
     */
    @SneakyThrows
    @Test
    public void testWaitingStateJoin() {
        //第一个线程睡眠
        Thread threadOne = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //第二个线程等待第一个线程
        Thread threadTwo = new Thread(() -> {
            try {
                //等待ThreadOne执行完毕
                threadOne.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadOne.start();
        threadTwo.start();
        Thread.sleep(1000);
        System.out.println("threadTwo state："+threadTwo.getState().name());
        Thread.sleep(10000);
    }

    /**
     * 测试WAITING
     * 使用LockSupport.park()
     */
    @SneakyThrows
    @Test
    public void testWaitingStateLockSupport() {
        //第一个线程阻塞
        Thread threadOne = new Thread(() -> {
            //开始阻塞
            LockSupport.park();
            System.out.println("threadOne unpark!!!");
            while (true){}
        });
        //第二个线程等待第一个线程
        Thread threadTwo = new Thread(() -> {
            try {
                Thread.sleep(3000);
                //取消阻塞（RUNNABLE->WAITING）
                LockSupport.unpark(threadOne);
                Thread.sleep(1000);
                System.out.println("threadOne unpark state："+threadOne.getState().name());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadOne.start();
        threadTwo.start();
        Thread.sleep(1000);
        System.out.println("threadOne state："+threadOne.getState().name());
        Thread.sleep(10000);
    }

    /**
     * 测试TIME_WAITING
     */
    @SneakyThrows
    @Test
    public void testTimeWaitintState() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(1000);
        //TIMED_WAITING
        System.out.println("thread state：" + thread.getState().name());
        Thread.sleep(10000);
    }

    /**
     * 测试TERMINATED
     */
    @SneakyThrows
    @Test
    public void testTerminatedState() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(1000);
        System.out.println(thread.getState().name());
    }


}
