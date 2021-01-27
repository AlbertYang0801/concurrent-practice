package com.albert.concurrent.threadpool;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * 处理集合的线程类
 * @author Albert
 * @date 2020/9/24 14:50
 */
public class FillListThread implements Callable<List<String>> {

    private List<Integer> list;

    public FillListThread(List<Integer> list) {
        this.list = list;
    }

    @Override
    public List<String> call() throws Exception {
        System.out.println("开始执行线程内的任务");
        Thread.sleep(5000);
        return list.stream().map(num -> String.valueOf(num * 10)).collect(Collectors.toList());
    }

}
