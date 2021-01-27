package com.albert.concurrent.lock.spinlock;

/**
 * 自旋锁的练习
 * 也是不可重入锁、非公平锁
 *
 * @author Albert
 * @date 2021/1/12 上午10:39
 */
public class NoReentrantSpinLock extends SpinLock {

    @Override
    public void lock() {
        Thread thread = Thread.currentThread();
        //当atomicReference为空时，将当前线程赋值给atomicReference（注意：第一个线程进入，while内条件为false，不会进入循环）
        while (!atomicReference.compareAndSet(null, thread)) {
        }
    }

    @Override
    public void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
    }


}


/**
 * 自旋锁定义：
 * 自旋锁是采用让当前线程不停地的在循环体内执行实现的，当循环的条件被其他线程改变时 才能进入临界区。如下
 *
 * 自旋锁的实现：
 * 1.第一个线程进入之后，请求lock()方法，可以正常拿到锁资源，不会进入到循环体。
 * 首次atomicReference默认为空，atomicReference.compareAndSet(null, thread)意为若atomicReference为null，则将thread赋值给atomicReference，并返回true。
 * 所以第一个线程不会进入到循环体，并正常执行。
 * 2.在第一个线程持有锁资源时，其他线程进入会不停的在循环体执行。
 * 因为在第一个线程不释放锁的情况下，atomicReference的值为第一个线程值，atomicReference.compareAndSet(null, thread)判断会返回false。
 * 然后线程会进入到循环体中。
 * 3.第一个线程请求unlock()方法，释放锁资源。
 * atomicReference.compareAndSet(thread,null)意为若atomicReference等于当前线程值，则将atomicReference赋值为null。
 * 4.当第一个线程请求unlock()方法之后，atomicReference的值变为null。
 * 其它在循环体的线程，atomicReference.compareAndSet(null, thread)判断为true，会跳出循环体，持有锁资源。
 * 注意：该锁为非公平锁。（多个在循环体里的线程，随机抢占锁，非公平）
 */