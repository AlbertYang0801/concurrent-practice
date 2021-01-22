package com.albert.concurrentpractice.threadcreate;

import com.albert.concurrentpractice.TestApplication;
import com.albert.concurrentpractice.threadpool.ThreadPoolCreate;
import com.albert.utils.jackson.JsonUtil;
import com.albert.utils.localdatetime.LocalDateTimeUtils;
import com.google.common.collect.Lists;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 线程创建的方式：第三种
 * implements Callable
 * 只能和线程池捆绑使用。
 *
 * @author Albert
 * @date 2020/8/12 16:38
 */
@Slf4j
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class CallableTest {

    /**
     * 测试Callable的返回值,使用匿名内部类；
     * 直接调用call()方法，未开启线程，相当于方法调用
     */
    @SneakyThrows
    @Test
    public void testCallable() {
        Callable<String> stringCallable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                int i = 1;
                return String.valueOf((++i));
            }
        };
        try {
            //没有开启线程，相当于直接方法调用
            String call = stringCallable.call();
            log.info("简单的方法调用，练习Callable接口匿名内部类的创建方式，结果：{}", call);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用实现Callable的类
     */
    @Test
    @SneakyThrows
    public void fillUserCallable(){
        FillUserCallable fillUserCallable = new FillUserCallable();
        //开启线程池提交任务
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<UserPO> future = executorService.submit(fillUserCallable);
        //获取返回结果
        UserPO userPO = future.get();
        System.out.println(JsonUtil.toString(userPO));
        executorService.shutdown();
    }

    /**
     * 使用lambda表达式，Callable是一个函数式接口
     */
    @SneakyThrows
    @Test
    public void testCallableLambda() {
        Callable<UserPO> userPOCallable = () -> {
            return UserPO.builder()
                    .name("测试返回参数")
                    .time(LocalDateTimeUtils.formatNow("yyyyMMddHHmmss"))
                    .build();
        };
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<UserPO> future = executorService.submit(userPOCallable);
        //获取返回结果
        UserPO userPO = future.get();
        System.out.println(JsonUtil.toString(userPO));
        executorService.shutdown();
        log.info("获取Callable接口返回的数据:{}", JsonUtil.toString(userPO));
    }

    /**
     * 测试Callable接口和线程池一起使用
     * Callable接口创建线程的方式
     */
    @SneakyThrows
    @Test
    public void testThreadPool() {
        Callable<UserPO> userPOCallable = () -> {
            return UserPO.builder()
                    .userId("110")
                    .name("测试lambda表达式")
                    .build();
        };
        //手动创建线程池
        ExecutorService executorService = new ThreadPoolExecutor(1, 2,
                0L, TimeUnit.SECONDS, new SynchronousQueue<>());
        //submit执行线程，获取返回结果
        Future<UserPO> submit = executorService.submit(userPOCallable);
        UserPO userPO = submit.get();
        log.info(JsonUtil.toString(userPO));
        //关闭线程池
        executorService.shutdown();
    }

    /**
     * 测试线程池的invokeALl()方法，提交全部Callable任务
     */
    @SneakyThrows
    @Test
    public void testThreadPoolInvokeAllTask() {
        //1.创建任务列表
        List<Callable<List<String>>> taskList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            List<Integer> list = Lists.newArrayList();
            for (int j = 0; j < 10; j++) {
                list.add(i * 10 + j);
            }
            //2.使用lambda表达式，创建callable线程
            Callable<List<String>> fillListThread = () -> list.stream().map(num -> String.valueOf(num * 10)).collect(Collectors.toList());
            taskList.add(fillListThread);
        }
        //3.创建线程池
        ExecutorService executorService = ThreadPoolCreate.getThreadPoolByAlibaba(5, 5);
        List<String> list = Lists.newArrayList();
        //4.使用线程池提交所有任务
        List<Future<List<String>>> futures = executorService.invokeAll(taskList);
        //5.遍历任务返回结果
        futures.forEach(future -> {
            //6.获取任务返回结果
            try {
                List<String> data = future.get();
                list.addAll(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //7.关闭线程池
        executorService.shutdown();
        System.out.println(JsonUtil.toString(list));
    }

    /**
     * 测试线程池的invokeAny()方法，提交所有任务，只返回第一个任务的结果
     */
    @SneakyThrows
    @Test
    public void testInvokeany() {
        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
        Callable<String> oneCallable = () -> "线程1执行完成";
        Callable<String> twoCallable = () -> "线程2执行完成";
        List<Callable<String>> callableList = Lists.newArrayList();
        callableList.add(oneCallable);

        callableList.add(twoCallable);
        String msg = executorService.invokeAny(callableList);
        System.out.println(msg);
    }


}


/**
 * 实现Callable的类
 */
class FillUserCallable implements Callable<UserPO> {

    @Override
    public UserPO call() throws Exception {
        return UserPO.builder()
                .userId("110")
                .name("测试实现类创建")
                .build();
    }
}


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class UserPO {

    private String userId;
    private String name;
    private String time;


}
