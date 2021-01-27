package com.albert.concurrent.threadpool;

import com.albert.concurrent.TestApplication;
import com.albert.utils.jackson.JsonUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * 使用线程池执行Callable线程
 *
 * @author Albert
 * @date 2020/9/24 14:48
 */
@Slf4j
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class ThreadPoolCallable {

    /**
     * 创建一个Callable线程类，使用线程池执行所有任务，并获取线程返回结果
     */
    @Test
    public void threadPoolOperateList() {
        //1.创建任务列表
        List<Callable<List<String>>> taskList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            //添加任务
            List<Integer> list = Lists.newArrayList();
            for (int j = 0; j < 10; j++) {
                list.add(i * 10 + j);
            }
            //创建callable线程
            FillListThread fillListThread = new FillListThread(list);
            taskList.add(fillListThread);
        }
        //2.创建线程池
        ExecutorService executorService = ThreadPoolCreate.getThreadPoolByAlibaba(5, 5);
        List<String> list = Lists.newArrayList();
        try {
            //3.使用线程池执行所有任务
            List<Future<List<String>>> futures = executorService.invokeAll(taskList);
            futures.forEach(future -> {
                try {
                    //获取任务返回结果
                    List<String> data = future.get();
                    list.addAll(data);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        System.out.println(JsonUtil.toString(list));
    }

    /**
     * 使用lambda表达式创建Callable，添加到任务列表，使用线程池执行
     */
    @Test
    public void threadPoolLambdaOperateList() {
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
        try {
            //4.使用线程池提交所有任务
            List<Future<List<String>>> futures = executorService.invokeAll(taskList);
            futures.forEach(future -> {
                try {
                    //5.获取任务返回结果
                    List<String> data = future.get();
                    list.addAll(data);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //6.关闭线程池
        executorService.shutdown();
        System.out.println(JsonUtil.toString(list));
    }


}
