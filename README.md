# Java工程师成长计划-高并发学习总结

[toc]


## 线程基础操作

### 多线程三大特性
#### 原子性、可见性、有序性


[原子性练习](src/test/java/com/albert/concurrentpractice/book/chapterone/ThreadAtomicity_01.java)

[参考博客：并发三大特性](https://www.cnblogs.com/weixuqin/p/11424688.html)

### 创建线程

#### Thread
#### Ruunable
#### Callable

[线程的创建方式练习](src/main/java/com/albert/concurrentpractice/basic/create)


### 线程停止
### 线程中断
### 线程等待(wait)和通知(notify)
### 挂起(suspend)和继续执行(resume)
### 等待(join)和礼让线程(yeild)
### volatile关键字
### 线程组
### 守护线程
### 线程优先级


ThreadFactory



## 线程池
线程池实战
线程池扩展
Fork/Join框架



## synchronized关键字

[synchronized关键字的练习](src/main/java/com/albert/concurrentpractice/synchronizedprac)



## 锁

自旋锁
可重入锁
公平锁/非公平锁
Condition
读写锁



## 并发控制工具
Semaphore
CountdownLatch
循环栅栏 CyclicBarrier
LockSupport阻塞工具




ReadLimiter限流
 

## 线程安全集合

CopyOnWriteArrayList
BlockQueue阻塞队列
SkipList跳表



## 参考书籍

[实战Java高并发程序设计（第2版）](https://item.jd.com/12458866.html)