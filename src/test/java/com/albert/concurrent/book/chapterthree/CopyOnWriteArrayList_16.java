package com.albert.concurrent.book.chapterthree;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 读写分离集合的练习
 * 线程安全
 * @author Albert
 * @date 2021/1/19 上午10:54
 */
public class CopyOnWriteArrayList_16 {

    //读写分离list
    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    public static class ReadLength extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            while (true){
                Thread.sleep(1000);
                System.out.println("集合长度为：" + list.size());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadLength readLength = new ReadLength();
        readLength.start();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        Thread.sleep(5000);
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        ArrayList<Integer> oldList = Lists.newArrayList();
        oldList.add(1);
        CopyOnWriteArrayList<Integer> newList = new CopyOnWriteArrayList<Integer>(oldList);
        System.out.println(newList);

    }


}

/**
 * 读操作是不加锁的，而写操作加锁。
 * 实现了读取并行，而写操作是独占的。
 */