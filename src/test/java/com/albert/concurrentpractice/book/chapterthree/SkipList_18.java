package com.albert.concurrentpractice.book.chapterthree;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 跳表的练习
 * 保证集合内部元素是有序的
 * Map和Set都有跳表的实现，内部元素有序
 * @author Albert
 * @date 2021/1/21 下午4:30
 */
public class SkipList_18 {

    public static void main(String[] args) {
        //跳表的一种实现
        ConcurrentSkipListMap<Integer, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();
        for (int i = 0; i < 10; i++) {
            concurrentSkipListMap.put(i, i + "");
        }
        //按顺序打印
        for (Map.Entry<Integer, String> entry : concurrentSkipListMap.entrySet()) {
            System.out.println(entry.getKey()+" "+entry.getValue());
        }

        System.out.println("-------------------------------------------------------------");
        //跳表的另一种实现
        ConcurrentSkipListSet<Integer> concurrentSkipListSet= new ConcurrentSkipListSet<>();
        for(int i=0;i<10;i++){
            concurrentSkipListSet.add(i);
        }
        //按顺序打印
        for (Integer value : concurrentSkipListSet) {
            System.out.println(value);
        }

    }


}
