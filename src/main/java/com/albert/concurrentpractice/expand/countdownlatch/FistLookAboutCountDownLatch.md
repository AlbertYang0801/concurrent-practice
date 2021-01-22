## CountDownLatch学习

#### 1. CountDownLatch的简述   

        CountDownLatch 位于java.util.concurrent.CountDownLatch;
        CountDownLatch是线程相关的一个计数器。 CountDownLatch计数器的操作是原子性的，同时只有一个线程去
    操作这个计数器，所以同时只有一个线程能减少这个计数器里面的值。可以通过为CountDownLatch设置初始的数字作
    为计数，任何对象都可以调用await()方法，直到这个计数器的计数值被其他的线程减到0为止，调用await()方法的
    线程即可继续执行。
    

#### 2. CountDownLatch的例子

##### 2.1 有三个工人为老板干活，这个老板会在三个工人全部干完活之后，检查工作。

* 设计Worker类为工人，Boss为老板。
* 测试类为CountDownLatchTest，具体位置为：com.albert.study.test.countdownlatch;
    方法为testBossWatchWorker();
    在调用时指定计数器个数，Worker类调用countDown()方法，使计数器减1。
    Boss类调用await()方法，使Boss线程休眠，等待计数器减少到0时唤醒Boss类。


##### 2.2. 进行读写操作，读操作必须在写操作完成之后。

* 设计Read类为读操作，Write类为写操作。
* 测试类为CountDownLatchTest，具体位置为：com.albert.study.test.countdownlatch;
    方法为testRead()方法;


