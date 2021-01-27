package com.albert.concurrent.book.chapterthree;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join框架的练习
 * 分而治之
 *
 * @author Albert
 * @date 2021/1/18 下午5:13
 */
public class CountTask_14 extends RecursiveTask<Long> {

    private static final int THRESHOLD = 10000;

    private long start;
    private long end;

    public CountTask_14(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;

        boolean canCompute = (end - start) < THRESHOLD;
        if (canCompute) {
            for (long i = start; i < end; i++) {
                sum += i;
            }
        } else {
            //等分100份
            long step = (start + end) / 100;
            ArrayList<CountTask_14> subTasks = new ArrayList<>();
            long pos = start;
            for (int i = 0; i < 100; i++) {
                long lastOne = pos + step;
                if (lastOne > end) {
                    lastOne = end;
                }
                CountTask_14 countTask = new CountTask_14(pos, lastOne);
                pos += step + 1;
                subTasks.add(countTask);
                //线程提交执行
                countTask.fork();
            }
            for (CountTask_14 subTask : subTasks) {
                //等待线程执行完毕，并获取返回结果
                sum += subTask.join();
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask_14 countTask = new CountTask_14(0, 200000L);
        //使用分治提交线程
        ForkJoinTask<Long> result = forkJoinPool.submit(countTask);
        try {
            //获取返回结果
            Long res = result.get();
            System.out.println("sum = "+res);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }
    }


}

/**
 * ForkJoinTask有两个子类。
 * RecursiveTask是有返回值的，RecursiveAction是没有返回值的。
 */