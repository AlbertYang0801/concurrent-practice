package com.albert.concurrentpractice.book.chapterthree;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * 线程安全的集合练习
 * @author Albert
 * @date 2021/1/18 下午6:22
 */
public class ThreadSafeCollection_15 {

    public static void main(String[] args) {
        Map<String,String> oldMap = Maps.newHashMap();
        //使用Collections对map进行线程同步封装
        Map<String,String> safeMap = Collections.synchronizedMap(oldMap);

        //线程安全的Map
        ConcurrentMap<Object, Object> map = Maps.newConcurrentMap();


        ArrayList<Object> oldList = Lists.newArrayList();
        //使用Collections对list进行线程同步封装
        List<Object> safeList = Collections.synchronizedList(oldList);
    }


}


/**
 * Collections封装的线程安全的集合，是因为其内部有个object对象，以该对象作为锁加在集合的相关方法上。
 * 在每个方法执行之前都要获得对象锁，效率较低。
 */
