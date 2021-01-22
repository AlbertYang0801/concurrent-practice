package com.albert.concurrentpractice.book.chapterthree;

import lombok.SneakyThrows;

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
    }


}

/**
 * 读操作是不加锁的，而写操作加锁。
 * 实现了读取并行，而写操作是独占的独占的。
 */