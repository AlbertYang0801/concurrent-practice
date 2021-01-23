# Java工程师成长计划-高并发学习总结

[toc]



---


## 一、多线程基础

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

### 线程创建的三种方式

-  Thread
> 不推荐使用。Java是单继承，所以不推荐使用继承来实现并发类。
> 注意：直接调用run()方法，相当于调用了该方法，没有开启新线程。只有调用start()方法，才是开启了一个新线程和主线程争夺资源。
-  Ruunable
> 推荐使用。没有结果返回，可以作为Thread类的参数创建线程，也可以与线程池捆绑使用。
-  Callable
> 推荐使用，有结果返回。可与FutureTask搭配使用，也可以与线程池捆绑使用，搭配Future获取任务执行完成的返回值。

参考：[线程的三种创建方式练习目录](src/main/java/com/albert/concurrentpractice/basic/create)


### 线程停止
- stop()方法(不推荐使用)
> stop()方法被调用的时候，会直接释放线程拥有的锁对象，这样会破坏临界区的原子性。

参考：[stop()方法的练习](src/test/java/com/albert/concurrentpractice/book/chaptertwo/ThreadStop_01.java)

- stop()方法的优化
> 优化stop()方法，在调用时，不直接释放锁资源，保证临界区资源执行完成后再释放锁资源。

参考：[stop()方法的优化练习](src/test/java/com/albert/concurrentpractice/book/chaptertwo/ThreadStopResolve_02.java)


### 线程中断
>线程中断并不会立即将线程退出，而是发出一个中断信号。目标线程接收中断信号后，如何退出由目标线程的逻辑决定。

Java中Thread提供了关于线程中断的三个方法：
 * interrupt() 中断线程
 * isInterrupted() 判断线程中断的状态
 * interrupted() 判断线程中断的状态，并重置中断标志(实际是调用了isInterrupted()方法，并传入中断标志数据)

参考：[线程中断的相关练习](src/test/java/com/albert/concurrentpractice/book/chaptertwo/ThreadInterruption_03.java)


### 线程等待(wait)和通知(notify)
>wait()方法和notify()方法是Object类里的方法，意味着任何对象都可以调用这两个方法。wait()方法使用时会释放锁对象，进入等待。而notify会随机唤醒一个等待的线程，被唤醒的线程会重新竞争锁对象。还有一个方法notifyAll()，会唤醒所有进入等待的线程。注意：不论是wait()方法还是notify()方法，都需要获取锁对象才能调用。


参考：[wait()和notify()的相关练习](src/test/java/com/albert/concurrentpractice/book/chaptertwo/ThreadWaitAndNotify_04.java)

>Thread.sleep()方法和Object.wait()一样也可以让线程等待，而sleep()可以指定等待时间，wait()可以被唤醒。还有一个主要区别，wait()会释放目标对象的锁，而sleep()不会释放任何资源。

### 挂起(suspend)和继续执行(resume)

- suspend()会阻塞当前线程，但是不会释放锁对象。（不推荐使用）
- resume()会取消当前线程的阻塞状态。

参考：[suspend()和resume()的相关练习](src/test/java/com/albert/concurrentpractice/book/chaptertwo/ThreadSuspendAndResume_05.java)


### 等待线程结束(join)和礼让线程(yeild)

- join()方法
> 等待调用线程执行结束。源码分析：实际上是调用了wait()方法在当前实例上，实现线程等待。而线程执行完成之前会调用notifyAll()方法通知等待线程继续执行。
- yeild()方法
>让出线程资源,但是会重新竞争。

参考：[join()和yeild的练习](src/test/java/com/albert/concurrentpractice/book/chaptertwo/ThreadJoin_06.java)



### volatile关键字

- volatile变量可保证可见性，但不保证原子性。volatile修饰变量时，会把该线程本地内存中的该变量刷新到主存中。
- volatile变量会禁止指令重排。

参考：[volatile关键字练习](src/test/java/com/albert/concurrentpractice/book/chaptertwo/ThreadVolatile_07.java)

参考博客：[Java volatile关键字最全总结：原理剖析与实例讲解(简单易懂)
](https://blog.csdn.net/u012723673/article/details/80682208?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param)


### 线程组

可按照功能将不同线程分组。

参考：[线程组的练习](src/test/java/com/albert/concurrentpractice/book/chaptertwo/ThreadGroup_08.java)

### 守护线程
守护线程是一种特殊的线程，会在所有的用户线程执行完成之后，随之结束。

参考：[守护线程的练习](src/test/java/com/albert/concurrentpractice/book/chaptertwo/ThreadDaemon_09.java)


### 线程优先级
线程之间抢占资源时，线程优先级越高，机会越大。

参考：[线程优先级的练习](src/test/java/com/albert/concurrentpractice/book/chaptertwo/ThreadPriority_10.java)

---


```
  _  _     ___     ___     _       ___    _____   _  _     ___     ___     ___     ___      ___    ___     ___     _     
 | || |   | __|   | __|   | |     / _ \  |_   _| | || |   | _ \   | __|   /   \   |   \    | _ \  / _ \   / _ \   | |    
 | __ |   | _|    | _|    | |__  | (_) |   | |   | __ |   |   /   | _|    | - |   | |) |   |  _/ | (_) | | (_) |  | |__  
 |_||_|   |___|   |___|   |____|  \___/   _|_|_  |_||_|   |_|_\   |___|   |_|_|   |___/   _|_|_   \___/   \___/   |____| 
```


---
## 二、线程池

### 线程池的七个参数
### 线程池的调度过程
### JDK对线程池的支持
### 线程池实战
### 自定义ThreadFactory
### 线程池扩展
### Fork/Join(分而治之)线程池框架





---

## 三、Synchronized关键字

[synchronized关键字的练习](src/main/java/com/albert/concurrentpractice/synchronizedprac)


---
## 四、Lock&Condition

### 自旋锁
### 可重入锁/不可重入锁
### 公平锁/非公平锁
### 读写锁ReadWriteLock
### Condition


--- 
## 五、并发控制工具

### CountdownLatch
### Semaphore
### 循环栅栏 CyclicBarrier
### LockSupport阻塞工具
### ReadLimiter限流
 
---
## 六、并发容器

### 线程安全的HashMap
### 线程安全的list
### CopyOnWriteArrayList
### BlockQueue阻塞队列
### SkipList跳表







## 参考书籍

[实战Java高并发程序设计（第2版）](https://item.jd.com/12458866.html)