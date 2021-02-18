

# Java工程师成长计划-高并发学习总结
```
         _______________________________________________        
        |   _      __        __                         |       
________|  | | /| / / ___   / / ____ ___   __ _  ___    |_______
\       |  | |/ |/ / / -_) / / / __// _ \ /  ' \/ -_)   |      /
 \      |  |__/|__/  \__/ /_/  \__/ \___//_/_/_/\__/    |     / 
 /      |_______________________________________________|     \ 
/__________)                                        (__________\
```


* [Java工程师成长计划-高并发学习总结](#java工程师成长计划-高并发学习总结)
  * [一、多线程基础](#一、多线程基础)
    * [多线程三大特性](#多线程三大特性)
    * [线程创建的三种方式](#线程创建的三种方式)
    * [线程停止](#线程停止)
    * [线程中断](#线程中断)
    * [线程等待(wait)和通知(notify)](#线程等待wait和通知notify)
    * [挂起(suspend)和继续执行(resume)](#挂起suspend和继续执行resume)
    * [等待线程结束(join)和礼让线程(yeild)](#等待线程结束join和礼让线程yeild)
    * [volatile关键字](#volatile关键字)
    * [线程组](#线程组)
    * [守护线程](#守护线程)
    * [线程优先级](#线程优先级)
  * [二、线程池](#二、线程池)
    * [线程池的调度过程](#线程池的调度过程)
    * [线程池创建时的七个参数](#线程池创建时的七个参数)
    * [四种拒绝策略](#四种拒绝策略)
      * [直接抛出异常：AbortPolicy](#直接抛出异常：abortpolicy)
      * [调用当前线程：CallerRunsPolicy](#调用当前线程：callerrunspolicy)
      * [不做处理： DiscardPolicy](#不做处理：-discardpolicy)
      * [删除队列任务： DiscardOldestPolicy](#删除队列任务：-discardoldestpolicy)
    * [JDK对线程池的支持](#jdk对线程池的支持)
    * [常见线程池](#常见线程池)
      * [缓存型线程池：CachedThreadPool](#缓存型线程池：cachedthreadpool)
      * [定长型线程池： FixedThreadPool](#定长型线程池：-fixedthreadpool)
      * [单线程线程池：SingleThreadExecutor](#单线程线程池：singlethreadexecutor)
      * [定时线程池：ScheduledThreadPool](#定时线程池：scheduledthreadpool)
      * [抢占式线程池：WorkStealingPool](#抢占式线程池：workstealingpool)
    * [线程池实战](#线程池实战)
    * [Fork/Join(分而治之)线程池框架](#forkjoin分而治之线程池框架)
  * [三、synchronized关键字](#三、synchronized关键字)
    * [同步代码块](#同步代码块)
    * [同步方法](#同步方法)
    * [synchronized和Lock的区别](#synchronized和lock的区别)
  * [四、Lock&Condition](#四、lockcondition)
    * [自旋锁](#自旋锁)
    * [可重入锁/不可重入锁](#可重入锁不可重入锁)
    * [公平锁/非公平锁](#公平锁非公平锁)
    * [重入锁ReentrantLock](#重入锁reentrantlock)
    * [重入锁的好搭档：Condition](#重入锁的好搭档：condition)
    * [读写锁ReadWriteLock](#读写锁readwritelock)
  * [五、并发控制工具](#五、并发控制工具)
    * [倒计数器CountdownLatch](#倒计数器countdownlatch)
    * [信号量Semaphore](#信号量semaphore)
    * [循环栅栏 CyclicBarrier](#循环栅栏-cyclicbarrier)
    * [LockSupport阻塞工具](#locksupport阻塞工具)
    * [ReadLimiter限流](#readlimiter限流)
  * [六、并发容器](#六、并发容器)
    * [线程安全的HashMap](#线程安全的hashmap)
    * [线程安全的list](#线程安全的list)
    * [CopyOnWriteArrayList](#copyonwritearraylist)
    * [BlockQueue阻塞队列](#blockqueue阻塞队列)
    * [SkipList跳表](#skiplist跳表)
  * [参考书籍](#参考书籍)




```
 __    __   _______  __       __        ______          .___________. __    __  .______       _______     ___       _______  
|  |  |  | |   ____||  |     |  |      /  __  \         |           ||  |  |  | |   _  \     |   ____|   /   \     |       \ 
|  |__|  | |  |__   |  |     |  |     |  |  |  |  ______`---|  |----`|  |__|  | |  |_)  |    |  |__     /  ^  \    |  .--.  |
|   __   | |   __|  |  |     |  |     |  |  |  | |______|   |  |     |   __   | |      /     |   __|   /  /_\  \   |  |  |  |
|  |  |  | |  |____ |  `----.|  `----.|  `--'  |            |  |     |  |  |  | |  |\  \----.|  |____ /  _____  \  |  '--'  |
|__|  |__| |_______||_______||_______| \______/             |__|     |__|  |__| | _| `._____||_______/__/     \__\ |_______/ 
```

```
              _,,,_                                       
            .'     `'.                                    
           /     ____ \      Fucking NullPointerException 
           |  .-'_  _\/    /                              
           \_/   a  a|    /                               
           (,`     \ |         .----.                     
            |     -' |        /|     '--.                 
             \   '=  /        ||    ]|   `-.              
             /`-.__.'         ||    ]|    ::|             
          .-'`-.__ \__        ||    ]|    ::|             
         /        ``  `.      ||    ]|    ::|             
       _ |     \     \  \     \|    ]|   .-'              
      / \|      \    |   \     L.__  .--'(                
     |   |\      `.  |    \  ,---|_      \---------,      
     |   | '.      './\    \/ .--._|=-    |_      /|      
     |   \   '.     `'.'. /`\/ .-'          '.   / |      
     |   |     `'.     `;-:-;`)|             |-./  |      
     |   /_       `'--./_  ` )/'-------------')/)  |      
     \   | `""""----"`\//`""`/,===..'`````````/ (  |      
      |  |            / `---` `==='          /   ) |      
      /  \           /                      /   (  |      
     |    '------.  |'--------------------'|     ) |      
      \           `-|                      |    /  |      
       `--...,______|                      |   (   |      
              | |   |                      |    ) ,|      
              | |   |                      |   ( /||      
              | |   |                      |   )/ `"      
             /   \  |                      |  (/          
           .' /I\ '.|                      |  /)          
        .-'_.'/ \'. |                      | /            
        ```  `"""` `| .-------------------.||             
                    `"`                   `"`             
```



## 一、多线程基础
### 多线程三大特性
---

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


参考：[原子性练习](src/test/java/com/albert/concurrent/book/chapterone/ThreadAtomicity_01.java)


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
---
-  Thread
> 不推荐使用。Java是单继承，所以不推荐使用继承来实现并发类。
> 注意：直接调用run()方法，相当于调用了该方法，没有开启新线程。只有调用start()方法，才是开启了一个新线程和主线程争夺资源。
-  Ruunable
> 推荐使用。没有结果返回，可以作为Thread类的参数创建线程，也可以与线程池捆绑使用。
-  Callable
> 推荐使用，有结果返回。可与FutureTask搭配使用，也可以与线程池捆绑使用，搭配Future获取任务执行完成的返回值。

参考：[线程的三种创建方式练习目录](src/main/java/com/albert/concurrent/basic/create)


### 线程停止
---
- stop()方法(不推荐使用)
> stop()方法被调用的时候，会直接释放线程拥有的锁对象，这样会破坏临界区的原子性。

参考：[stop()方法的练习](src/test/java/com/albert/concurrent/book/chaptertwo/ThreadStop_01.java)

- stop()方法的优化
> 优化stop()方法，在调用时，不直接释放锁资源，保证临界区资源执行完成后再释放锁资源。

参考：[stop()方法的优化练习](src/test/java/com/albert/concurrent/book/chaptertwo/ThreadStopResolve_02.java)


### 线程中断
---
>线程中断并不会立即将线程退出，而是发出一个中断信号。目标线程接收中断信号后，如何退出由目标线程的逻辑决定。

Java中Thread提供了关于线程中断的三个方法：
 * interrupt() 中断线程
 * isInterrupted() 判断线程中断的状态
 * interrupted() 判断线程中断的状态，并重置中断标志(实际是调用了isInterrupted()方法，并传入中断标志数据)

参考：[线程中断的相关练习](src/test/java/com/albert/concurrent/book/chaptertwo/ThreadInterruption_03.java)


### 线程等待(wait)和通知(notify)
---
>wait()方法和notify()方法是Object类里的方法，意味着任何对象都可以调用这两个方法。wait()方法使用时会释放锁对象，进入等待。而notify会随机唤醒一个等待的线程，被唤醒的线程会重新竞争锁对象。还有一个方法notifyAll()，会唤醒所有进入等待的线程。注意：不论是wait()方法还是notify()方法，都需要获取锁对象才能调用。


参考：[wait()和notify()的相关练习](src/test/java/com/albert/concurrent/book/chaptertwo/ThreadWaitAndNotify_04.java)

>Thread.sleep()方法和Object.wait()一样也可以让线程等待，而sleep()可以指定等待时间，wait()可以被唤醒。还有一个主要区别，wait()会释放目标对象的锁，而sleep()不会释放任何资源。

### 挂起(suspend)和继续执行(resume)
---
- suspend()会阻塞当前线程，但是不会释放锁对象。（不推荐使用）
- resume()会取消当前线程的阻塞状态。

参考：[suspend()和resume()的相关练习](src/test/java/com/albert/concurrent/book/chaptertwo/ThreadSuspendAndResume_05.java)


### 等待线程结束(join)和礼让线程(yeild)
---
- join()方法
> 等待调用线程执行结束。源码分析：实际上是调用了wait()方法在当前实例上，实现线程等待。而线程执行完成之前会调用notifyAll()方法通知等待线程继续执行。
- yeild()方法
>让出线程资源,但是会重新竞争。

参考：[join()和yeild的练习](src/test/java/com/albert/concurrent/book/chaptertwo/ThreadJoin_06.java)


### volatile关键字
---
- volatile变量可保证可见性，但不保证原子性。volatile修饰变量时，会把该线程本地内存中的该变量刷新到主存中。
- volatile变量会禁止指令重排。

参考：[volatile关键字练习](src/test/java/com/albert/concurrent/book/chaptertwo/ThreadVolatile_07.java)

参考博客：[Java volatile关键字最全总结：原理剖析与实例讲解(简单易懂)
](https://blog.csdn.net/u012723673/article/details/80682208?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param)


### 线程组
---
可按照功能将不同线程分组。

参考：[线程组的练习](src/test/java/com/albert/concurrent/book/chaptertwo/ThreadGroup_08.java)


### 守护线程
---
守护线程是一种特殊的线程，会在所有的用户线程执行完成之后，随之结束。

参考：[守护线程的练习](src/test/java/com/albert/concurrent/book/chaptertwo/ThreadDaemon_09.java)


### 线程优先级
---
线程之间抢占资源时，线程优先级越高，机会越大。

参考：[线程优先级的练习](src/test/java/com/albert/concurrent/book/chaptertwo/ThreadPriority_10.java)


---


```
 __    __   _______  __       __        ______          .___________. __    __  .______       _______     ___       _______  .______     ______     ______    __      
|  |  |  | |   ____||  |     |  |      /  __  \         |           ||  |  |  | |   _  \     |   ____|   /   \     |       \ |   _  \   /  __  \   /  __  \  |  |     
|  |__|  | |  |__   |  |     |  |     |  |  |  |  ______`---|  |----`|  |__|  | |  |_)  |    |  |__     /  ^  \    |  .--.  ||  |_)  | |  |  |  | |  |  |  | |  |     
|   __   | |   __|  |  |     |  |     |  |  |  | |______|   |  |     |   __   | |      /     |   __|   /  /_\  \   |  |  |  ||   ___/  |  |  |  | |  |  |  | |  |     
|  |  |  | |  |____ |  `----.|  `----.|  `--'  |            |  |     |  |  |  | |  |\  \----.|  |____ /  _____  \  |  '--'  ||  |      |  `--'  | |  `--'  | |  `----.
|__|  |__| |_______||_______||_______| \______/             |__|     |__|  |__| | _| `._____||_______/__/     \__\ |_______/ | _|       \______/   \______/  |_______|

```


## 二、线程池

### 线程池的调度过程
---
1. 根据初始化参数创建线程池，刚创建时，线程池内没有线程。
2. 当有新的任务进到线程池的时候，会立即新增线程执行任务。
3. 若线程数等于核心线程数时，这时进来的任务会被添加到任务队列中，而线程会从任务队列中获取任务执行。
4. 线程数等于核心线程数且任务队列已满，这时候会在线程池中创建新线程来执行任务。
5. 若线程数等于最大线程数，且任务队列已满，此时会执行线程池对应的拒绝策略。
6. 当任务队列中没有任务，且线程等待时间超过空闲时间，则该线程会被回收。最终线程池中的线程数量会保持在核心线程数的大小。




### 线程池创建时的七个参数
---
```
//源码
public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
       ......
    }
```

- int corePoolSize

>核心线程数。即使在空闲时也要保留在线程池中的线程数，除非设置了allowCoreThreadTimeOut。

- int maximumPoolSize
>最大线程数。当线程数大于核心线程数时，一个任务被提交到线程池后，首先会缓存到工作队列中，如果工作队列满了，则会在线程池中创建一个新线程，而线程数量会有一个最大数量的限制，即为maximumPoolSize。

- long keepAliveTime
>线程空闲时间。一个线程处于空闲，并且线程数量大于核心线程数，那么该线程会在指定时间后被回收，指定时间由keepAliveTime指定。

- TimeUnit unit
> 线程空闲时间单位。

- BlockingQueue<Runnable> workQueue
> 任务队列。当线程池没有空闲线程时，在执行任务之前将任务保存在队列中，该队列仅保存由execute方法提交的任务。

- ThreadFactory threadFactory
> 线程工厂，可设置线程为守护线程，自定义线程名称等。

- RejectedExecutionHandler handler
> 任务拒绝策略。当任务队列里的任务长度达到最大，线程池中的线程数量达到最大，就会执行任务拒绝策略。





### 四种拒绝策略
---
#### 直接抛出异常：AbortPolicy
>默认的任务拒绝策略，对于新增任务，拒绝处理，直接抛出RejectedExecutionException异常。

```
//源码
public static class AbortPolicy implements RejectedExecutionHandler {
    
    public AbortPolicy() { }

    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        //直接抛出异常
        throw new RejectedExecutionException("Task " + r.toString() +
                                             " rejected from " +
                                             e.toString());
    }
}
```

#### 调用当前线程：CallerRunsPolicy
>调用自己的线程来执行任务，不创建新的线程，而是用自己当前线程进行执行，会降低对于新任务的提交速度，影响整体性能。如果程序能够容许延时，并且不能丢弃每一个任务，即可采取这个策略。
```
//源码
public static class CallerRunsPolicy implements RejectedExecutionHandler {
   
    public CallerRunsPolicy() { }

    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        if (!e.isShutdown()) {
            //调用自己的线程执行
            r.run();
        }
    }
}
```


#### 不做处理： DiscardPolicy
>不做任何处理，直接丢掉该任务.
```
//源码
public static class DiscardPolicy implements RejectedExecutionHandler {
   
    public DiscardPolicy() { }

    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
    }
}
```

#### 删除队列任务： DiscardOldestPolicy
>删除任务队列中最早的任务，将新增任务添加到任务队列中。
```
//源码
public static class DiscardOldestPolicy implements RejectedExecutionHandler {
   
    public DiscardOldestPolicy() { }

    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        if (!e.isShutdown()) {
            //删除线程池的任务队列的第一个元素
            e.getQueue().poll();
            e.execute(r);
        }
    }
}
```

### JDK对线程池的支持
---
![imgae/ThreadPoolExecutor.png](https://cdn.jsdelivr.net/gh/AlbertYang0801/pic-bed@main/img/20210218105938.png)

ThreadPoolExecutor表示一个线程池，里面包含了创建线程池的实现。

[java.util.concurrent.Executors](jetbrains://idea/navigate/reference?project=concurrent-practice&fqn=java.util.concurrent.Executors)

Executors是一个线程池工厂，可以通过它获取一个具有特定功能的线程池。


### 常见线程池
---
>java从jdk1.5开始提供了线程池的四种类型：分别为CachedThreadPool、FixedThreadPool、ScheduledThreadPool、SingleThreadExecutor；从jdk1.8开始提供了WorkStealingPool。这5种线程池都位于Executors线程池工厂中。


注意：由于Executors线程池工厂创建出的线程存在一定弊端（具体见各个线程池的分析）,推荐使用手动创建的方式来创建线程池。（出自阿里规约）

参考：[常见线程池的练习](src/test/java/com/albert/concurrent/threadpool/ThreadPoolCreateTest.java)

#### 缓存型线程池：CachedThreadPool

>可灵活创建线程，如果线程池长度超过任务长度，可灵活回收线程。

```
//源码
public static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                  60L, TimeUnit.SECONDS,
                                  new SynchronousQueue<Runnable>());
}

public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                  60L, TimeUnit.SECONDS,
                                  new SynchronousQueue<Runnable>(),
                                  threadFactory);
}
```

- CachedThreadPool()线程池的创建原理：
>  实际上是创建了一个ThreadPoolExecutor()对象。
>- 指定核心线程数为0，即线程池最小的线程数为0；
>- 指定线程池最大允许存在的线程数为Integer.MAX_VALUE；
>- 指定空闲线程的销毁时间是60s；
>- 指定任务队列为同步队列SynchronousQueue只能包含一个任务的队列；
>- 线程工厂可使用默认的或自定义的线程工程；
>- 任务拒绝策略使用默认的ThreadPoolExecutor.AbortPolicy对于新增任务，拒绝处理，直接抛出RejectedExecutionException异常。

- CachedThreadPool()线程池的使用：任务队列只允许存放一个任务，线程池中若有任务进来，则立刻新建线程去执行任务。若有大量任务同时进来，则在线程池中新建对应的线程，若线程空闲60s，则会自动回收。

- CachedThreadPool()线程池的好处：由于CachedThreadPool()线程池允许线程数量很大，并且会自动回收，非常适合执行数量很大的短期任务。

- CachedThreadPool()线程池的弊端：允许的创建线程数量为Integer.MAX_VALUE，可能会创建大量的线程，从而导致OOM(内存溢出)。（出自阿里规约）





#### 定长型线程池： FixedThreadPool

>固定线程池的线程数量，控制线程数，多余的任务在任务队列中等待。

```
//源码
public static ExecutorService newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>());
}

public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>(),
                                  threadFactory);
}
```

- FixedThreadPool()线程池的创建原理:
>  实际上是创建了一个ThreadPoolExecutor()对象.
>- 指定核心线程数和最大线程数都为n，即线程池一直保持拥有着n个线程；
>- 指定空闲线程的销毁时间是0；
>- 指定任务队列为无界队列LinkedBlockingQueue，队列长度为Integer.MAX_VALUE的队列；
>- 线程工厂可使用默认的或自定义的线程工程；
>- 任务拒绝策略使用默认的ThreadPoolExecutor.AbortPolicy对于新增任务，拒绝处理，直接抛出RejectedExecutionException异常。

- FixedThreadPool()线程池的使用：线程池从初始化开始便恒定拥有n个线程，不存在线程个数的增减，任务队列允许放接近无穷的任务，即线程池没有线程可以处理新任务时，会将新任务加入任务队列中，该线程池任务的拒绝策略不会执行，因为任务队列被允许一直放入任务。

- FixedThreadPool()线程池的好处：由于FixedThreadPool()线程池线程数量恒定，非常适合执行时间长且任务量固定的任务。

- FixedThreadPool()线程池的弊端：允许的任务队列长度为Integer.MAX_VALUE，可能会堆积大量的任务请求，从而导致OOM(内存溢出)。（出自阿里规约）


#### 单线程线程池：SingleThreadExecutor

>线程池只有一个线程，若因为任务失败而终止当前线程，则新的线程会替代它继续执行后续任务。

```
//源码
public static ExecutorService newSingleThreadExecutor() {
    return new FinalizableDelegatedExecutorService
        (new ThreadPoolExecutor(1, 1,
                                0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>()));
}

public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
    return new FinalizableDelegatedExecutorService
        (new ThreadPoolExecutor(1, 1,
                                0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>(),
                                threadFactory));
}
```

- SingleThreadExecutor()线程池的创建原理：
>实际上是创建了一个ThreadPoolExecutor()对象。
>- 指定核心线程数和最大线程数都为1，即线程池一直保持拥有着1个线程；
>- 指定空闲线程的销毁时间是0；
>- 指定任务队列为队列长度为Integer.MAX_VALUE的队列；
>- 线程工厂可使用默认的或自定义的线程工程；
>- 任务拒绝策略使用默认的ThreadPoolExecutor.AbortPolicy对于新增任务，拒绝处理，直接抛出RejectedExecutionException异常。

- SingleThreadExecutor()线程池的使用：线程池只初始化并维护一个线程，并设置LinkedBlockingQueue为任务队列。

- SingleThreadExecutor()线程池的好处：使用SingleThreadExecutor来自动维护一个单线程。

- SingleThreadExecutor()线程池的弊端：允许的任务队列长度为Integer.MAX_VALUE，可能会堆积大量的任务请求，从而导致OOM(内存溢出)。（出自阿里规约）


#### 定时线程池：ScheduledThreadPool

> 可以定时执行任务。

```
//源码
public ScheduledThreadPoolExecutor(int corePoolSize) {
    super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
          new DelayedWorkQueue());
}

public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue) {
    this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
         Executors.defaultThreadFactory(), defaultHandler);
}
```

- ScheduledThreadPool()线程池的创建原理：
>- 实际上是创建了一个ThreadPoolExecutor()对象。
>- 可指定核心线程数。
>- 最大线程数为Integer.MAX_VALUE。
>- 指定空闲线程的销毁时间是0；
>- 指定任务队列为专门延时队列DelayedWorkQueue，来实现定时任务的执行。
>- 线程工厂可使用默认的或自定义的线程工程。 
>- 任务拒绝策略使用默认的ThreadPoolExecutor.AbortPolicy对于新增任务，拒绝处理，直接抛出RejectedExecutionException异常。


- ScheduledThreadPool()线程池的使用：可实现定时执行任务，或延时执行任务。

- ScheduledThreadPool()线程池的好处：可以定时周期的执行任务。

- ScheduledThreadPool()线程池的弊端：允许的线程最大长度为Integer.MAX_VALUE，可能会创建大量的线程，从而导致OOM(内存溢出)。（出自阿里规约）


#### 抢占式线程池：WorkStealingPool

>抢占式的线程池，能合理的使用CPU进行任务处理，适合很耗时的任务。

```
//源码
public static ExecutorService newWorkStealingPool() {
    return new ForkJoinPool
        (Runtime.getRuntime().availableProcessors(),
         ForkJoinPool.defaultForkJoinWorkerThreadFactory,
         null, true);
}

public static ExecutorService newWorkStealingPool(int parallelism) {
    return new ForkJoinPool
        (parallelism,
         ForkJoinPool.defaultForkJoinWorkerThreadFactory,
         null, true);
}
```

- WorkStealingPool()线程池的创建原理：
>- 实际上是创建了一个ForkJoinPool()对象。
>- 传入参数则使用传入的线程数量，若不传入，则默认使用当前计算机可用的CPU数量。


ForkJoinPool线程池的分析可见：[Fork/Join(分而治之)线程池框架](#forkjoin分而治之线程池框架)


### 线程池实战
---

参考：[线程池和Future的组合练习](src/test/java/com/albert/concurrent/threadpool/ThreadPoolCallable.java)


### Fork/Join(分而治之)线程池框架
---
>Fork/Join线程池框架包含ForkJoinPool线程池，ForkJoinTask任务类。作用是为了实现将大型复杂任务进行递归的分解，直到任务足够小才直接执行，从而递归的返回各个足够小的任务结果汇总成一个大任务的结果，以此类推得到最初提交的那个大型复杂任务的结果。



 ForkJoinTask有两个子类:
 - RecursiveTask是有返回值的。
 - RecursiveAction是没有返回值的。


参考：[ForkJoinPool线程池的练习](src/test/java/com/albert/concurrent/book/chapterthree/CountTask_14.java)

---
```
 __    __   _______  __       __        ______             _______.____    ____ .__   __.   ______  __    __  .______        ______   .__   __.  __   ________   _______  _______  
|  |  |  | |   ____||  |     |  |      /  __  \           /       |\   \  /   / |  \ |  |  /      ||  |  |  | |   _  \      /  __  \  |  \ |  | |  | |       /  |   ____||       \ 
|  |__|  | |  |__   |  |     |  |     |  |  |  |  ______ |   (----` \   \/   /  |   \|  | |  ,----'|  |__|  | |  |_)  |    |  |  |  | |   \|  | |  | `---/  /   |  |__   |  .--.  |
|   __   | |   __|  |  |     |  |     |  |  |  | |______| \   \      \_    _/   |  . `  | |  |     |   __   | |      /     |  |  |  | |  . `  | |  |    /  /    |   __|  |  |  |  |
|  |  |  | |  |____ |  `----.|  `----.|  `--'  |      .----)   |       |  |     |  |\   | |  `----.|  |  |  | |  |\  \----.|  `--'  | |  |\   | |  |   /  /----.|  |____ |  '--'  |
|__|  |__| |_______||_______||_______| \______/       |_______/        |__|     |__| \__|  \______||__|  |__| | _| `._____| \______/  |__| \__| |__|  /________||_______||_______/ 

```
```
       程序出Bug了？          
      　　　∩∩               
      　　（´･ω･）            
      　 ＿|　⊃／(＿＿_       
      　／ └-(＿＿＿／        
      　￣￣￣￣￣￣￣         
      算了反正不是我写的       
      　　 ⊂⌒／ヽ-、＿         
      　／⊂_/＿＿＿＿ ／       
      　￣￣￣￣￣￣￣         
      万一是我写的呢           
      　　　∩∩                
      　　（´･ω･）            
      　 ＿|　⊃／(＿＿_       
      　／ └-(＿＿＿／        
      　￣￣￣￣￣￣￣         
      算了反正改了一个又出三个  
      　　 ⊂⌒／ヽ-、＿        
      　／⊂_/＿＿＿＿ ／      
      　￣￣￣￣￣￣￣        
```
---

## 三、Synchronized关键字

[synchronized关键字的练习](src/main/java/com/albert/concurrent/synchronizedprac)


---
## 四、Lock&Condition

### 自旋锁

---

>定义：自旋锁是采用让当前线程不停地的在循环体内执行实现的，当循环的条件被其他线程改变时 才能进入临界区。

- 使用原子引用变量AtomicReference<V>实现自旋锁。

```
public class SpinLock {

    /**
     * 原子引用变量
     */
    private static AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock() {
        Thread thread = Thread.currentThread();
        //当atomicReference为空时，将当前线程赋值给atomicReference（注意：第一个线程进入，while内条件为false，不会进入循环）
        while (!atomicReference.compareAndSet(null, thread)) {
        }
    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
    }


}
```
注意：该例子为不可重入锁，且为非公平锁，获得锁的先后顺序，不会按照进入lock的先后顺序进行(可重入锁和公平锁的实现见下方章节)。



### 可重入锁/不可重入锁

---


- 不可重入锁：与可重入相反，获取锁后不能重复获取，否则会死锁（自己锁自己）。

参考：[基于自旋锁实现的不可重入锁的练习](src/main/java/com/albert/concurrent/lock/spinlock/NoReentrantSpinLock.java)

- 可重入锁：当线程获取某个锁后，还可以继续获取它，可以递归调用，而不会发生死锁；


参考：[基于自旋锁实现的可重入锁练习](src/main/java/com/albert/concurrent/lock/spinlock/ReentrantSpinLock.java)

```
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
```

**基于自旋锁可以实现可重入锁和不可重入锁。**

参考：[可重入锁和不可重入锁的对比练习](src/main/java/com/albert/concurrent/lock/reentrant/SpinLockPractice.java)



### 公平锁/非公平锁

---

- 非公平锁：已经获取锁对象的线程有更大概率继续持有相同的锁对象。
  - 优点：执行效率高
  - 缺点：容易造成饥饿现象。
  
- 公平锁：多个线程会按照顺序执行
  - 优点：不会造成饥饿现象。
  - 缺点：需要维护一个有序队列，实现成本高，性能低下。

注意：synchronized关键字实现的同步，锁对象是非公平的。

**在上方介绍自旋锁部分，基于原子引用变量AtomicReference<V>实现的自旋锁是一个非公平锁。**

可参考：[非公平锁的的实现练习](src/main/java/com/albert/concurrent/lock/spinlock/NoReentrantSpinLock.java)

**以该类为基础进行优化，维护一个有序队列实现公平锁。**

参考：[公平锁的实现练习](src/main/java/com/albert/concurrent/lock/spinlock/NoReentrantFairSpinLock.java)

**基于自旋锁可以实现公平锁和非公平锁。**

参考：[公平锁锁和非公平锁的对比练习](src/main/java/com/albert/concurrent/lock/fair/FairSpinLockPractice.java)



### 重入锁ReentrantLock
---
>特点：可重入、可中断、可实现公平锁、可获取锁状态。

1. 可重入。
>可多次获取锁对象，但是释放锁的次数要和获取锁的次数保持一致。
- 若获取锁对象比释放的次数多。则当前线程会一直持有锁对象而不释放，其他线程会因为拿不到锁对象而无法进入临界区。
- 若释放锁的次数比获取锁对象的次数多，则会产生IllegalMonitorStateException异常。

2. 可中断。
>提供了lockInterruptibly()方法；获取锁之后，若有中断发生，会响应中断，停止获取锁对象，并释放已有锁。

**中断可有效解决线程间的死锁问题，线程限时等待请求锁也可以有效解决死锁问题。**

3. 可实现公平锁。
```
//创建锁对象时，指定为true，即可实现公平锁。
ReentrantLock fairLock = new ReentrantLock(true);
```

**主要方法：**
- lock()方法：获得锁，如果锁已经被占用，则等待。
- unlock()方法：释放锁。
- tryLock()方法：尝试获得锁，如果成功返回true，失败返回false。该方法不等待，立即返回。
- tryLock(long timeout, TimeUnitunit)方法：在指定时间内尝试获得锁，如果成功返回true，失败返回false。**(使用此方法申请锁，可有效避免死锁问题)**
- isHeldByCurrentThread()方法：判断当前线程是否持有该锁。

**参考练习：**

- [重入锁的练习](src/test/java/com/albert/concurrent/book/chapterthree/ReenterLock_01.java)

- [重入锁中断特性的练习](src/test/java/com/albert/concurrent/book/chapterthree/LockInterrupt_02.java)

- [限时等待锁的练习-指定等待时间](src/test/java/com/albert/concurrent/book/chapterthree/LockTime_03.java)

- [限时等待锁的练习-不指定等待时间](src/test/java/com/albert/concurrent/book/chapterthree/LockTime_04.java)

- [公平锁的练习](src/test/java/com/albert/concurrent/book/chapterthree/FairLock_05.java)

### 重入锁的好搭档：Condition
--- 
>Condition是和重入锁搭配使用的，类似于wait()和notify()方法。Object.wait()和Object.notify()方法是于synchronized搭配使用的，而Condition是与重入锁搭配使用的。通过lock接口的newCondition()方法即可创建一个与当前锁绑定的Condition对象，利用该对象，就可以实现让线程在合适时机等待或得到通知。

- 主要方法：
```
void await() throws InterruptedException;

void awaitUninterruptibly();

boolean await(long time, TimeUnit unit) throws InterruptedException;

boolean awaitUntil(Date deadline) throws InterruptedException;

void signal();

void signalAll();
```
- await()方法会使当前线程等待，并释放锁。当其他线程使用signal()方法或者signalAll()方法时，线程会被唤醒并开始竞争锁资源。当线程被中断时，也能跳出等待。
- awaitUninterruptibly()和await()方法基本一致，区别是在等待过程中不会响应中断。
- signal()用于唤醒一个在等待中的线程，调用该方法的线程必须拥有锁对象，否则会报异常。
- signalAll()方法会唤醒所有在等待中的线程。

参考：[Condition的练习](src/test/java/com/albert/concurrent/book/chapterthree/Condition_06.java)


### 读写锁ReadWriteLock
---
>锁可分为排他锁和共享锁。synchronized和ReentrantLock都是排他锁，只允许线程独占资源。而在多个线程进行读操作的时候，单个线程占用资源进行读取，
其他需要读取的线程会进行等待，而这种等待是不合理的。读写锁ReadWriteLock就是针对读操作和写操作进行的锁优化，具体优化如下。

- 读-读不互斥：并发执行读操作，提高效率。
- 读-写互斥：读会阻塞写，写也会阻塞读。
- 写-写互斥：写线程会独占。

读写锁需要注意：

1. 读锁与读锁之间是不互斥的，读锁与写锁之间是互斥的。
2. 写锁与其它锁都是互斥的。
3. 保证写锁是独占资源的。
4. 读线程之间是并发执行的，而写线程执行的时候是独占的。能提高读线程的执行效率。


与可重入锁比较：
- 可重入锁是互斥的。
- 将读线程和写线程的锁换成可重入锁，之后线程会按照顺序执行，执行效率变慢。

参考：[读写锁的练习和可重入锁做效率对比](src/test/java/com/albert/concurrent/book/chapterthree/ReadWriteLock_08.java)



--- 
## 五、并发控制工具

### 倒计数器CountdownLatch
---

>CountDownLatch是线程相关的一个计数器，CountDownLatch计数器的操作是原子性的，同时只有一个线程去操作这个计数器，所以同时只能有一个线程能减少这个计数器里面的值。可以通过为CountDownLatch设置初始值，任何对象都可以调用await()方法，直到这个计数器的初始值被其他的线程减到0为止，调用await()方法的线程即可继续执行。

- CountDownLatch 位于java.util.concurrent.CountDownLatch

主要方法：

```
//指定初始值
public CountDownLatch(int count);
//计时器倒数，即计数器减1
public void countDown();
//线程休眠，等到计数器countDownLatch为0时唤醒线程，继续执行
public void await() throws InterruptedException ;
```

**倒计数器CountDownLatch例子练习：**

例1：老板监督工人练习。
>有三个工人为老板干活，这个老板会在三个工人全部干完活之后，检查工作。

* 设计Worker类为工人，Boss为老板。
* 在调用时指定计数器个数，Worker类调用countDown()方法，使计数器减1。Boss类调用await()方法，使Boss线程休眠，等待计数器减少到0时唤醒Boss类。
* 测试类为CountDownLatchTest，方法为testBossWatchWorker()。

例2：使用CountDownLatch实现多线程按照顺序执行。
>进行读写操作，读操作必须在写操作完成之后进行。

* 设计Read类为读操作，Write类为写操作。
* 测试类为CountDownLatchTest，方法为testRead()方法。


参考：[CountDownLatch例子练习](src/main/java/com/albert/concurrent/expand/countdownlatch)

### 信号量Semaphore
---
>信号量是锁的增强，无论是内部锁synchronized和重入锁ReentrantLock，一次都只是允许一个线程访问一个资源。而信号量可以指定多个线程，同时访问某一个资源。信号量既提供了同步机制，又可以控制同时最大访问的个数。

- Semaphore 位于java.util.concurrent.Semaphore


- 构造方法：

```
//指定同时最大访问个数
Semaphore semaphore = new Semaphore(5);
//可以指定同时最大访问个数和是否公平
Semaphore semaphore = new Semaphore(5, true);
```

- 公平信号量：
指的是获得锁的顺序与调用semaphore.acquire()的顺序有关，但不代表百分百获得信号量，仅仅在概率上能得到保证。

- 常用方法：
```
//请求获取许可，如果未响应，则线程会等待。直到线程有释放许可或者中断发生。
public void acquire()
//和acquire()方法类似，但是不响应中断。
public void acquireUninterruptibly()
//尝试获取许可，若成功返回true，获取不成功返回false。不会等待，立即返回。
public Boolean tryAcquire()
//尝试在指定时间内获取许可，若成功返回true，获取不成功返回false。超过指定时间则不继续等待，立即返回。
public Boolean tryAcquire(long timeout, TimeUnit unit)
//释放一个许可，让其它等待的线程可以访问资源。（可以使信号量的许可总数加1）
public void release()
//返回信号量当前可用许可个数
public int availablePermits()
```

参考：[信号量Semaphore的练习](src/test/java/com/albert/concurrent/book/chapterthree/Semaphore_07.java)


**信号量Semaphore例子练习：**

例1：停车场问题。
>停车场只有10个车位，现在有30辆车去停车。当车位满时出来一辆车才能有一辆车进入停车。

参考：[停车场问题的练习](src/main/java/com/albert/concurrent/expand/semaphore/ParkingCars.java)

例2：使用信号量Semaphore实现多线程按照顺序执行。
>产品、开发、测试同时来上班，产品给需求之后，开发才可以开始开发，开发完成之后，测试才可以开始测试。按照产品->开发->测试的顺序执行。

参考：[信号量Semaphore实现多线程按照顺序执行的练习](src/main/java/com/albert/concurrent/expand/semaphore/SemaphoreOrder.java)

### 循环栅栏 CyclicBarrier
---
>CyclicBarrier是一种多线程并发控制工具，可循环利用，作用是让所有线程都等待完成后才会进行下一步行动。

1. 构造方法：
```
public CyclicBarrier(int parties)
public CyclicBarrier(int parties, Runnable barrierAction)
```
>- 第一个构造方法可指定参与线程的个数。
>- 第二种构造方法可以指定当CyclicBarrier完成一次计数之后，需要执行的任务。

2. 重要方法：
```
public int await() throws InterruptedException, BrokenBarrierException
public int await(long timeout, TimeUnit unit) throws InterruptedException, BrokenBarrierException, TimeoutException
```
> await()方法，表示线程已经到达栅栏，准备执行。等到约定的线程数都到达之后，即计数完成，开始往下执行。
若有指定需要在计数完成后指定的任务，则先执行指定的任务。

3. CyclicBarrier和CountDownLatch的区别:

>- CountDownLatch是一次性的，而CyclicBarrier是可循环利用的。
>- CountDownLatch参与线程的职责是不一样的，await()是在等待倒计时结束，countDown是进行一次倒计时。
>- CyclicBarrier参与的线程的职责都是在等待计数结束。



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

- [实战Java高并发程序设计（第2版）](https://item.jd.com/12458866.html)



```
         ,--"""",--.__,---[],-------._                             
       ,"   __,'            \         \--""""""==;-                
     ," _,-"  "/---.___     \       ___\   ,-'',"                  
    /,-'      / ;. ,.--'-.__\  _,-"" ,| `,'   /                    
   /``""""-._/,-|:\       []\,' ```-/:;-. `. /                     
             `  ;:::      ||       /:,;  `-.\                      
                =.,'__,---||-.____',.=                             
                =(:\_     ||__    ):)=                             
               ,"::::`----||::`--':::"._                           
             ,':::::::::::||::::::::::::'.                         
    .__     ;:::.-.:::::__||___:::::.-.:::\     __,                
       """-;:::( O )::::>_|| _<::::( O )::::-"""                   
   =======;:::::`-`:::::::||':::::::`-`:::::\=======               
    ,--"";:::_____________||______________::::""----.          , , 
         ; ::`._(    |    |||     |   )_,'::::\_,,,,,,,,,,____/,'_,
       ,;    :::`--._|____[]|_____|_.-'::::::::::::::::::::::::);_ 
      ;/ /      :::::::::,||,:::::::::::::::::::::::::::::::::::/  
     /; ``''''----------/,'/,__,,,,,____:::::::::::::::::::::,"    
     ;/                :);/|_;| ,--.. . ```-.:::::::::::::_,"      
    /;                :::):__,'//""\\. ,--.. \:::,:::::_,"         
   ;/              :::::/ . . . . . . //""\\. \::":__,"            
   ;/          :::::::,' . . . . . . . . . . .:`::\                
   ';      :::::::__,'. ,--.. . .,--. . . . . .:`::`               
   ';   __,..--'''-. . //""\\. .//""\\ . ,--.. :`:::`              
   ;    /  \\ .//""\\ . . . . . . . . . //""\\. :`::`              
   ;   /       . . . . . . . . . . . . . . . . .:`::`              
   ;   (          . . . . . . . . . . . . . . . ;:::`              
   ,:  ;,            . . . . . . . . . . . . . ;':::`              
   ,:  ;,             . . . . . . . . . . . . .;`:::               
   ,:   ;,             . . . . . . . . . . . . ;`::;`              
    ,:  ;             . . . . . . . . . . . . ;':::;`              
     :   ;             . . . . . . . . . . . ,':::;                
      :   '.          . . . . . . . .. . . .,':::;`                
       :    `.       . . . . . . . . . . . ;::::;`                 
        '.    `-.   . . . . . . . . . . ,-'::::;                   
          `:_    ``--..___________..--'':::::;'`                   
             `._::,.:,.:,:_ctr_:,:,.::,.:_;'`                      
________________`"\/"\/\/'""""`\/"\/""\/"___________               
```
