# Java工程师成长计划-高并发学习总结

[toc]



---


## 多线程基础操作

### 多线程三大特性
1.  原子性
>原子性是指在一个操作中就是cpu不可以在中途暂停然后再调度，既不被中断操作，要不执行完成，要不就不执行。
如果一个操作时原子性的，那么多线程并发的情况下，就不会出现变量被修改的情况
比如 a=0；（a非long和double类型） 这个操作是不可分割的，那么我们说这个操作时原子操作。再比如：a++； 这个操作实际是a = a + 1；是可分割的，所以他不是一个原子操作。
非原子操作都会存在线程安全问题，需要我们使用同步技术（sychronized）来让它变成一个原子操作。一个操作是原子操作，那么我们称它具有原子性。java的concurrent包下提供了一些原子类，我们可以通过阅读API来了解这些原子类的用法。比如：AtomicInteger、AtomicLong、AtomicReference等。
（由Java内存模型来直接保证的原子性变量操作包括read、load、use、assign、store和write六个，大致可以认为基础数据类型的访问和读写是具备原子性的。如果应用场景需要一个更大范围的原子性保证，Java内存模型还提供了lock和unlock操作来满足这种需求，尽管虚拟机未把lock与unlock操作直接开放给用户使用，但是却提供了更高层次的字节码指令monitorenter和monitorexit来隐匿地使用这两个操作，这两个字节码指令反映到Java代码中就是同步块—synchronized关键字，因此在synchronized块之间的操作也具备原子性。）

- Java中的原子性
  -  除了long和double类型的赋值操作。
  > 
      在32位长度操作系统中，long和double类型的赋值不是原子操作。
      因为long和double都是64位的，在32位系统上，对long和double类型的数据进行读写都要分为两步完成，
      若同时两个线程同时写一个变量内存，一个写低8位，一个写高8位，就会导致无效数据出现。
      解决办法：long和double类型声明为volatile。java的内存模型保证声明为volatile的long和double变量的get和set操作是原子的。
  - 所有引用reference的赋值操作(入AtomicReference)。
  - java.concurrent.Atomic.* 包中所有类的一切操作。
  
- 如何保证原子性 ：
  - 使用synchronized关键字定义同步代码块或同步方法来保证原子性。
  - 受用lock加锁来保证原子性。
  - 使用Atomic相关类保证原子性。



参考：[原子性练习](src/test/java/com/albert/concurrentpractice/book/chapterone/ThreadAtomicity_01.java)


2. 可见性
>当一个线程修改了共享变量的值，其他线程能够看到修改的值。
Java 内存模型是通过在变量修改后将新值同步回主内存，在变量读取前从主内存刷新变量值这种依赖主内存作为传递媒介的方法来实现可见性的。

- 如何保证可见性：
    - 通过 volatile 关键字标记变量保证可见性。
    - 使用synchronized关键字定义同步代码块或同步方法来保证可见性。
    - 使用lock加锁来保证可见性。
    - 使用Atomic相关类保证可见性。
    - 通过final关键字来保证可见性。


3. 有序性
>即程序执行的顺序按照代码的先后顺序执行。
java存在指令重排，所以存在有序性问题。

- 如何保证有序性：
  - 通过 volatile 关键字标记变量保证有序性。
  - 使用synchronized关键字定义同步代码块或同步方法来保证有序性。
  - 使用lock加锁来保证有序性。



参考博客：[并发三大特性](https://www.cnblogs.com/weixuqin/p/11424688.html)

### 创建线程

-  Thread
> 不推荐使用。Java是单继承，所以不推荐使用继承来实现并发类。
> 注意：直接调用run()方法，相当于调用了该方法，没有开启新线程。只有调用start()方法，才是开启了一个新线程和主线程争夺资源。
-  Ruunable
> 推荐使用。没有结果返回，可以作为Thread类的参数创建线程，也可以与线程池捆绑使用。
-  Callable
> 推荐使用，有结果返回。可与FutureTask搭配使用，也可以与线程池捆绑使用，搭配Future获取任务执行完成的返回值。

[线程的三种创建方式练习目录](src/main/java/com/albert/concurrentpractice/basic/create)


### 线程停止
- stop()方法(不推荐使用)
> stop()方法被调用的时候，会直接释放线程拥有的锁对象，这样会破坏临界区的原子性。

演示见：[stop()方法的练习](src/test/java/com/albert/concurrentpractice/book/chaptertwo/ThreadStop_01.java)

- stop()方法的优化
> 优化stop()方法，在调用时，不直接释放锁资源，保证临界区资源执行完成后再释放锁资源。

演示见：[stop()方法的优化练习](src/test/java/com/albert/concurrentpractice/book/chaptertwo/ThreadStopResolve_02.java)


### 线程中断





### 线程等待(wait)和通知(notify)
### 挂起(suspend)和继续执行(resume)
### 等待(join)和礼让线程(yeild)
### volatile关键字
### 线程组
### 守护线程
### 线程优先级


ThreadFactory


---
## 线程池
线程池实战
线程池扩展
Fork/Join框架



## synchronized关键字

[synchronized关键字的练习](src/main/java/com/albert/concurrentpractice/synchronizedprac)



## Lock&Condition


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