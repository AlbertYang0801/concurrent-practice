package com.albert.concurrent.lock.spinlock;

/**
 * 重入锁的练习
 * 基于NoReentrantSpinLock(不可重入锁)实现重入锁
 * 可重入锁、非公平锁
 * @author Albert
 * @date 2021/1/12 上午11:43
 */
public class ReentrantSpinLock extends SpinLock {

    private static int count = 0;

    @Override
    public void lock() {
        Thread thread = Thread.currentThread();
        //如果引用变量等于当前线程，计数器加1
        if (atomicReference.get() == thread) {
            count++;
            return;
        }
        while (!atomicReference.compareAndSet(null, thread)) {
        }
    }

    @Override
    public void unlock() {
        Thread thread = Thread.currentThread();
        if(atomicReference.get()==thread){
            //如果计数器为0，释放锁资源
            if(count==0){
                atomicReference.compareAndSet(thread,null);
                return;
            }
            count--;
        }
    }


}


/**
 * 可重入锁的实现：
 * 第一个线程首次获取锁资源时，设置引用变量值为第一个线程值。若第一个线程再次获取相同锁资源，将锁计数器加1，允许重复获取锁。
 * 当线程释放锁时，若锁计数器不为0，则锁计数器减1。当锁计数器为0时，重置引用变量值为空，释放锁资源。
 */
