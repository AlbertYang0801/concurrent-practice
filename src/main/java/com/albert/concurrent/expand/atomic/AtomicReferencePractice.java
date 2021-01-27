package com.albert.concurrent.expand.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * AtomicReference类练习
 * 提供了一个可以原子读写的对象引用变量
 *
 * @author Albert
 * @date 2021/1/12 上午11:05
 */
public class AtomicReferencePractice {

    private static AtomicReference voidAtomicReference() {
        return new AtomicReference();
    }

    private static AtomicReference stringAtomicReference(String data) {
        return new AtomicReference(data);
    }

    /**
     * AtomicReference的简单操作
     * 获取值和赋值操作
     */
    private static void simple() {
        AtomicReference atomicReference = stringAtomicReference("text");
        //获取值
        String data = atomicReference.get().toString();
        System.out.println(data);
        //赋值
        atomicReference.set("update text");
        System.out.println(atomicReference.get().toString());
    }

    private static void compare() {
        String oldValue = "old value";
        String newValue = "new value";
        AtomicReference atomicReference = new AtomicReference(oldValue);
        System.out.println("============开始比较==================");
        //先比较，若比较成功，则进行赋值操作，并返回true。比较不成功，则直接返回false
        boolean one = atomicReference.compareAndSet(oldValue, newValue);
        if (one) {
            System.out.println("1.将atomicReference值与oldValue进行比较，若比较成功继续进行！");
            System.out.println("2.比较成功之后，将newValue的值赋值给atomicReference");
            System.out.println(atomicReference.get().toString());
        }

        System.out.println("============开始第二次比较==================");
        System.out.println("1.比较之前atomicReference的值为:"+atomicReference.get().toString());
        boolean two = atomicReference.compareAndSet(oldValue, newValue);
        System.out.println("2.将atomicReference的值和oldValue进行比较。");
        System.out.println(two);
        System.out.println("3.结果为false，atomicReference的值没有改变");
        System.out.println(atomicReference.get().toString());

    }

    public static void main(String[] args) {
        //简单操作
        simple();
        //比较赋值
        compare();
    }


}
