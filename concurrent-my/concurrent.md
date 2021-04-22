

* [使用线程](#使用线程)
* [基础线程机制](#基础线程机制)
* [Executor](#executor)
* [中断](#中断)
* [互斥同步](#互斥同步)
	* [synchronized](#synchronized)
	* [ReentrantLock](#reentrantlock)
* [线程之间的协作](#线程之间的协作)
* [线程不安全的例子](#线程不安全的例子)
* [java内存模型](#java内存模型)
* [线程安全的实现](#线程安全的实现)
* [JVM对synchronized的优化](#jvm对synchronized的优化)
* [一些实践](#一些实践)



### 使用线程
有三种使用线程的方法：实现Runnable接口、实现Callable接口、继承Thread抽象类
#### 线程的6种状态
- 新建
- 可运行
- 阻塞：等待获取锁，从而进入synchroonized块等代码
- 无限期等待：调用join方法、wait等方法
- 限期等待：调用sleep方法、调用join方法(带时间参数)、调用wait方法(带时间参数)
- 死亡

#### Runnable接口
```java
public static class MyRun implements Runnable {

        @Override
        public void run() {
            System.out.println("run");
        }
    }

    public static void main(String[] args) {
        MyRun myRun = new MyRun();
        Thread thread = new Thread(myRun);
        thread.start();
    }
```
#### Callable接口
与Runnable相比，Callable有返回值，返回值通过FutureTask进行封装
```java
public static class MyCall implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            return 123;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<Integer> ft = new FutureTask<>(new MyCall());
        new Thread(ft).start();
        String result = ft.get(2000L, TimeUnit.MILLISECONDS);
        System.out.println(result);
    }
```

#### 继承Thread抽象类

```java
public class MyThread extends Thread {
    public void run() {
        // ...
    }
}
public static void main(String[] args) {
    MyThread mt = new MyThread();
    mt.start();
}
```

#### 方法的比较
一般我们实现接口而不是继承Thread类，因为java不能多继承，如果继承了Thread则无法继承其他类，java是可以多实现接口的；类可能只要求能够执行就可以了，继承Thread开销过大

---
### 基础线程机制


#### Daemon
守护程序是程序运行时在后台提供服务的线程，当所有非守护线程结束时，程序结束，同时会杀死守护线程，守护线程不能够持有需要关闭的资源，比如打开文件等，当JVM退出的时候，守护线程没有时间来关闭文件，会导致数据缺失

在线程启动之前使用setDaemon()方法可以将一个线程设置为守护线程

```java
public static void main(String[] args) {
    Thread thread = new Thread(new MyRunnable());
    thread.setDaemon(true);
}
```

#### sleep()
Thread.sleep(millisec)方法会休眠当前正在执行的线程，sleep可能会抛出InterruptedException

```java
public void run() {
    try {
        Thread.sleep(3000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

#### yield()
静态方法Thread.yield()的调用声明了当前线程已经完成了生命周期中最重要的部分，可以切换给其他线程执行

---
### Executor
Executor管理多个异步任务的执行，程序员无需显示地管理线程的生命周期，这儿的异步是指多个任务执行互不干扰，不需要进行同步操作

通常都是使用Executors类提供的静态工厂方法，来创建不同的线程池，包括以下几种：
- newCachedThreadPool：用来处理大量短时间工作任务的线程池，它会试图缓存线程并重用，如果没有缓存的线程可以使用，那么就创建新的线程；当线程闲置超过60s后会被终止并移出缓存。corePoolSize=0， maximumPoolSize=Integer.MAX.VALUE
- newFixedThreadPool：这个线程池的线程数量是固定的，当线程都在使用而来了新的任务，那么需要等待空闲线程。corePoolSize=nThreads， maximumPoolSize=nThreads
- newScheduledThreadPool：创建一个定长线程池，支持定时和周期性任务执行
- newSingleThreadExecutor：相当于大小为1的FixedThreadPool，因此所有的任务都是顺序执行的。corePoolSize=1， maximumPoolSize=1
- newWorkStealingPool：其内部会创建ForkJoinPool，利用Work-Stealing算法，并行处理任务，不保证处理顺序
```java
public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0; i < 5; i++) {
        executorService.execute(new MyRunnable());
    }
    executorService.shutdown();
}
```

```java
ThreadPoolExecutor.class类

private final HashSet<ThreadPoolExecutor.Worker> workers = new HashSet();   //存储了工作线程，工作线程执行工作任务，Worker类提供了管理线程创建、销毁的方法

//上面这几个线程池内部都是调用了ThreadPoolExecutor的构造函数来创建的
public ThreadPoolExecutor(
int corePoolSize,   //核心线程数，长期驻留的线程数量，在newCachedThreadPool中是0，在newFixedThreadPool中是nThreads
int maximumPoolSize,    //线程最大数量，在newFixedThreadPool中是nThreads，在newCachedThreadPool中是Integer.MAX_VALUE
long keepAliveTime,     //空闲线程闲置最大时间
TimeUnit unit,          //时间单位
BlockingQueue<Runnable> workQueue,  //工作队列，存储各个任务
ThreadFactory threadFactory,    //提供了创建线程的逻辑
RejectedExecutionHandler handler    //任务提交被拒绝的处理逻辑
)

public void execute(Runnable command) {
    int c = this.ctl.get();
    //检查工作线程数量，低于则创建Worker
    if (workerCountOf(c) < this.corePoolSize) {
        if (this.addWorker(command, true)) {
            return;
        }
    
        c = this.ctl.get();
    }
    //isRunning()判断线程池是否被shutdown
    //工作队列可能是有界的，offer()比较友好
    if (isRunning(c) && this.workQueue.offer(command)) {
        int recheck = this.ctl.get();
        //二次检查
        if (!isRunning(recheck) && this.remove(command)) {
            this.reject(command);
        } else if (workerCountOf(recheck) == 0) {
            this.addWorker((Runnable)null, false);
        }
    } else if (!this.addWorker(command, false)) {
        this.reject(command);
    }
}
``` 

#### ScheduledThreadPool
在这个线程池提交的线程会反复执行
```java
// 2秒后开始执行定时任务，每3秒执行:
//FixedRate是指任务总是以固定时间间隔触发，不管任务执行多长时间
ses.scheduleAtFixedRate(new Task("fixed-rate"), 2, 3, TimeUnit.SECONDS);
// 2秒后开始执行定时任务，以3秒为间隔执行:
//FixedDelay是指，上一次任务执行完毕后，等待固定的时间间隔，再执行下一次任务：
ses.scheduleWithFixedDelay(new Task("fixed-delay"), 2, 3, TimeUnit.SECONDS);
```

#### 线程池实践
- 不要使用Executors来创建线程池，而是使用ThreadPoolExecutor来手动配置线程数量和使用有界的队列
    - FixedThreadPool 和 SingleThreadExecutor ： 允许请求的队列长度为 Integer.MAX_VALUE,可能堆积大量的请求，从而导致 OOM。
    - CachedThreadPool 和 ScheduledThreadPool ： 允许创建的线程数量为 Integer.MAX_VALUE ，可能会创建大量线程，从而导致 OOM。
- 避免任务堆积：如果线程池的工作线程数量太少，导致了处理速度跟不上入队速度，导致任务处理不了堆积在内存中，甚至出现OOM。可以使用jmap工具查看是否有大量的任务对象入队
- 线程泄露：如果线程数量不断增长(使用jstack工具检查)，可能是任务逻辑有问题，导致工作线程不能被释放，需要排查下线程栈，可能很多线程都是卡在同一个代码段中
- 避免死锁等同步问题
- 避免操作ThreadLocal
- 线程池大小：CPU密集型则为CPU核心数+1，如果是IO密集型则为2倍CPU核心数

#### 线程池默认的行为
- 不会初始化 corePoolSize 个线程，有任务来了才创建工作线程；
- 当核心线程满了之后不会立即扩容线程池，而是把任务堆积到工作队列中；
- 当工作队列满了后扩容线程池，一直到线程个数达到 maximumPoolSize 为止；
- 如果队列已满且达到了最大线程后还有任务进来，按照拒绝策略处理；
- 当线程数大于核心线程数时，线程等待 keepAliveTime 后还是没有任务需要处理的话，收缩线程到核心线程数。

### 中断
通过调用一个线程的interrupt()来中断线程，如果线程处于阻塞、限期等待或无限期等待状态，那么就会抛出InterruptedException，从而提前结束该线程。但是不能中断IO阻塞和synchronized锁阻塞

```java
public class InterruptExample {

    private static class MyThread1 extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                System.out.println("Thread run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
public static void main(String[] args) throws InterruptedException {
    Thread thread1 = new MyThread1();
    thread1.start();
    thread1.interrupt();
    System.out.println("Main run");
}
```
```
Main run
java.lang.InterruptedException: sleep interrupted
    at java.lang.Thread.sleep(Native Method)
    at InterruptExample.lambda$main$0(InterruptExample.java:5)
    at InterruptExample$$Lambda$1/713338599.run(Unknown Source)
    at java.lang.Thread.run(Thread.java:745)
```

如果一个线程的run()方法执行一个无线循环，并且没有执行sleep()等会抛出InterruptedException的操作，那么调用线程的interrupt方法就无法使线程提前结束，但是调用interrupt方法会设置线程的中断标记，此时调用interrupted()方法会返回true

```java
public class InterruptExample {

    private static class MyThread2 extends Thread {
        @Override
        public void run() {
            while (!interrupted()) {
                // ..
            }
            System.out.println("Thread end");
        }
    }
}
public static void main(String[] args) throws InterruptedException {
    Thread thread2 = new MyThread2();
    thread2.start();
    thread2.interrupt();
}
```
```
Thread end
```

#### Executor的中断操作
调用Executor的shutdown()方法会等待线程执行完毕后再关闭，而调用shutdownNow()方法则相当于调用每个线程的interrupt()方法
```java
public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.execute(() -> {
        try {
            Thread.sleep(2000);
            System.out.println("Thread run");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });
    executorService.shutdownNow();
    System.out.println("Main run");
}
Main run
java.lang.InterruptedException: sleep interrupted
    at java.lang.Thread.sleep(Native Method)
    at ExecutorInterruptExample.lambda$main$0(ExecutorInterruptExample.java:9)
    at ExecutorInterruptExample$$Lambda$1/1160460865.run(Unknown Source)
    at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
    at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
    at java.lang.Thread.run(Thread.java:745)
```

如果只想中断Executor中的一个线程，通过submit()方法来提交一个线程，它会返回Future<?>对象，通过调用该对象的cancel(true)方法来中断线程
```java
Future<?> future = executorService.submit(() -> {
    // ..
});
future.cancel(true);
```

### 互斥同步
java提供了两种锁机制来控制多个线程对共享资源的互斥访问，第一个是JVM实现的synchronized，另一个是JDK实现的ReentrantLock

#### synchronized
##### 同步一个代码块
```java
public void func() {
    synchronized (this) {
        // ...
    }
}
```
它只作用于一个对象，如果调用两个对象上的同步代码块，则不会进行同步

下面的代码使用ExecutorService执行了两个线程，由于调用的是一个对象的同步代码块，因此会进行同步，当一个线程进入同步语句块时，另一个线程就必须等待
```java
public class SynchronizedExample {

    public void func1() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
            }
        }
    }
}
public static void main(String[] args) {
    SynchronizedExample e1 = new SynchronizedExample();
    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.execute(() -> e1.func1());
    executorService.execute(() -> e1.func1());
}
0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9
```

下面的代码，两个线程调用了不同对象的同步代码块，因此这两个线程就不需要同步
```java
public static void main(String[] args) {
    SynchronizedExample e1 = new SynchronizedExample();
    SynchronizedExample e2 = new SynchronizedExample();
    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.execute(() -> e1.func1());
    executorService.execute(() -> e2.func1());
}
0 0 1 1 2 2 3 3 4 4 5 5 6 6 7 7 8 8 9 9
```

##### 同步一个方法
它和同步一个代码块一样，作用于一个对象
```java
public synchronized void func () {
    // ...
}
```

##### 同步一个类
```java
public void func() {
    synchronized (SynchronizedExample.class) {
        // ...
    }
}
```

作用于整个类，两个线程调用同一个的不同对象上的同步语句，也会进行同步
```java
public class SynchronizedExample {

    public void func2() {
        synchronized (SynchronizedExample.class) {
            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
            }
        }
    }
}
public static void main(String[] args) {
    SynchronizedExample e1 = new SynchronizedExample();
    SynchronizedExample e2 = new SynchronizedExample();
    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.execute(() -> e1.func2());
    executorService.execute(() -> e2.func2());
}
0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9
```

##### 同步一个静态方法
作用于整个类
```java
public synchronized static void fun() {
    // ...
}
```

#### ReentrantLock
ReentrantLock是java.util.concurrent包中的锁
```java
public static class LockExample {
        private Lock lock = new ReentrantLock();
        public void func() {
            lock.lock();
            try {
                for (int i = 0; i< 10; i++) {
                    System.out.print(i+" ");
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        LockExample lockExample = new LockExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> lockExample.func());
        executorService.execute(() -> lockExample.func());
    }
0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 
```

#### 公平锁和非公平锁
1. 非公平锁在调用lock()的时候，会调用Unsafe的CAS操作，如果此时资源没有被锁上，那么可以直接获取到锁；
2. 如果资源已经被上锁，那么会和公平锁一样调用tryAcquire()方法，非公平锁如果发现此时资源没有上锁，那么会第二次执行CAS；而公平锁会判断等待队列中是否有其他线程在等待，如果有则插入到等待队列中
3. 非公平锁和公平锁如果都没有获取锁，那么就进入到阻塞队列等待唤醒

#### 比较
synchronized | ReentrantLock
---|---
JVM实现 | JDK实现
新版本java对其进行了很多优化，两个性能现在差不多了 | 
当持有锁的线程长期不释放锁的时候，等待的线程可以选择放弃，改为处理其他事情，不可中断 | 可中断
公平锁是多个线程在等待同一个锁的时候，必须按照申请锁的时间顺序来依次获得锁，非公平的 | 默认是非公平的，但是也可以公平
1个condition | 一个ReentrantLock可以同时绑定多个Condition对象

除非需要使用ReentrantLock的高级功能，否则优先使用synchronized，JVM原生地支持它，ReentrantLock不是所有的JDK版本都支持。使用synchronized不用担心没有释放锁而导致死锁的问题，因为JVM会自动释放它


### 线程之间的协作
多个线程一起工作去解决某个问题，A部分必须在B部分之前完成，那么需要对线程进行协调。

#### join()
在线程当中调用另一个线程join()方法，会将当前线程挂起，执行另一个线程的内容

在下面的代码中，虽然threadB是先启动的，但是在run方法中调用了threadA的join方法，因此threadB线程会被挂起，需要等待threadA执行结束才继续执行
```java
public static class ThreadA extends Thread {
        public void run() {
            System.out.println("Thread A");
        }
    }
    public static class ThreadB extends Thread {
        private ThreadA threadA;
        public ThreadB(Thread a) {
            threadA = (ThreadA) a;
        }
        public void run() {
            try {
                threadA.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread B");
        }
    }

    public static void main(String[] args) {
        ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB(threadA);
        threadB.start();
        threadA.start();
    }
// A B
```


#### wait() notify()和notifyAll()
调用wait的线程会被挂起，直到其他线程执行notify方法或notifyAll方法唤醒挂起的线程，它们属于Object类，不属于Thread类；只能在同步块或者同步方法中使用，否则在使用的时候会抛出异常java.lang.IllegalMonitorStateException

线程调用了wait方法后会释放当前占用的锁资源，如果没有释放锁，那么其他线程无法进入对象的同步方法或者同步控制块中，无法执行notify方法或者notifyAll方法来唤醒挂起的线程，造成死锁

```java
public static class WaitNotifyExample {
        public synchronized void before() {
            System.out.println("Before");
            notifyAll();
        }
        public synchronized void after() {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("After");
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        WaitNotifyExample waitNotifyExample = new WaitNotifyExample();
        executorService.execute(() -> waitNotifyExample.after());
        executorService.execute(() -> waitNotifyExample.before());
    }
/*
Before
After
*/
```

##### wait()和sleep()的区别
1. wait()是Object的方法，sleep()是Thread的静态方法
2. wait()会释放锁，而sleep()不释放锁

#### await() signal() signalAll()
concurrent类库中提供了Condition类来协调线程，可以再condition对象上调用await方法来挂起线程，其他线程调用signal方法或者signalAll方法来唤醒挂起的线程

相比较于wait方法，await方法可以指定等待的条件，因此更加灵活
```java
//需要配合lock进行加锁和解锁，只有await和signal会报错：java.lang.IllegalMonitorStateException
public static class AwaitSignalExample {
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        public void before() {
            lock.lock();
            System.out.println("Before");
            condition.signalAll();
            lock.unlock();
        }
        public void after() {
            lock.lock();
            try {
                condition.await();
                System.out.println("After");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        AwaitSignalExample a = new AwaitSignalExample();
        executorService.execute(() -> a.after());
        executorService.execute(() -> a.before());
    }
/*
Before
After
*/
```



### 线程不安全的例子
如果多个线程对同一个共享数据进行访问而不采取同步操作的话，那么操作的结果是不一致的。

以下代码演示了 1000 个线程同时对 cnt 执行自增操作，操作结束之后它的值有可能小于 1000。
```java
public static class UnSafeExample {
        private int cnt = 0;
        public void add() {
            cnt++;
        }
        public int get() {
            return cnt;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int numThread = 1000;
        UnSafeExample unSafeExample = new UnSafeExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(numThread);
        for (int i = 0; i < numThread; i++) {
            executorService.execute(() -> {
                unSafeExample.add();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println(unSafeExample.get());
    }
```

### java内存模型
- java内存模型试图屏蔽各种硬件和操作系统的内存访问差异，实现java程序在各个平台下都能够达到一致的内存访问效果
- 保证多线程程序的正确性

处理器上的寄存器读写速度比内存的速度快了几个数量级，为了解决速度不一致，在它们之间加入了高速缓存，但是这也导致了缓存不一致的问题

所有的变量都存储在主内存中，而线程有自己的工作内存，工作内存保存了线程使用的变量数据，工作内存存储在高速缓存或者寄存器中
 
JVM内部的实现通常是依赖于内存屏障，通过禁止某些重排序的方式，提供内存可见性保证，也就是各种happen-before规则。与此同时，需要尽量保证各种编译器、计算机体系结构能够提供一致的行为 
 
java内存模型定义了8个操作来完成主内存和工作内存之间的交互操作
- read：把一个变量的值从主内存传输到工作内存中
- load：在 read 之后执行，把 read 得到的值放入工作内存的变量副本中
- use：把工作内存中一个变量的值传递给执行引擎
- assign：把一个从执行引擎接收到的值赋给工作内存的变量
- store：把工作内存的一个变量的值传送到主内存中
- write：在 store 之后执行，把 store 得到的值放入主内存的变量中
- lock：作用于主内存的变量
- unlock

#### 内存模型的三大特性
1. 原子性：java内存模型保证了8个操作都具有原子性。但是java内存模型允许虚拟机将没有被volatile修饰的64位数据的读写操作划分为两次32位的操作来进行

使用AtomicInteger重写之前线程不安全的代码可以得到线程安全的代码实现
```java
public class AtomicExample {
    private AtomicInteger cnt = new AtomicInteger();

    public void add() {
        cnt.incrementAndGet();
    }

    public int get() {
        return cnt.get();
    }
}
public static void main(String[] args) throws InterruptedException {
    final int threadSize = 1000;
    AtomicExample example = new AtomicExample(); // 只修改这条语句
    final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0; i < threadSize; i++) {
        executorService.execute(() -> {
            example.add();
            countDownLatch.countDown();
        });
    }
    countDownLatch.await();
    executorService.shutdown();
    System.out.println(example.get());
}
```
2. 可见性：当一个线程修改了共享变量的值，其他线程能够立即看到新的值。java内存模型在变量修改后就将新值同步回主内存，其他线程在读取变量的时候从主内存刷新变量值实现可见性

实现可见性的方法：1) volatile。当变量被其修饰的时候，对它的修改会立刻刷新到主内存中，其他变量读取数据的时候会去主内存中读取；2) synchronized，对一个变量执行unlock操作之前，必须把变量值同步回主内存；3) final

3. 有序性：线程内的操作都是有序的。无序是因为发生了指令重排，java内存模型中，允许编译器和处理器对指令进行重排，重排不会影响单线程下应用的执行，但是会影响多线程并发执行的正确性

volatile关键字通过添加内存屏障的方式禁止指令重排

也可以通过synchronized来保证有序性，它保证每个时刻只有一个线程执行同步代码

#### 先行发生原则
也叫作happen-before关系，是java内存模型中保证多线程操作可见性的机制

可以用volatile和syncronized来保证有序性。除此之外，JVM还规定了先行发生原则，让一个操作无需控制就能先于另一个操作完成。


1. 单一线程原则：在一个线程内，在程序前面的操作先行发生于后面的操作
2. 管程锁定规则：一个unlock操作先行发生于后面对同一个锁对象的lock操作
3. volatile变量规则：对一个volatile变量的写操作先行发生于对这个变量的读操作
4. Thread对象的start方法调用先行与发生于此线程的每一个动作
5. Thread对象的结束先行发生于join方法的返回
6. 对线程interrupt方法的调用先行发生于被中断线程的代码检测到中断事件的发生，可以通过interrupted方法检测到是否有中断发生
7. 一个对象的初始化完成(构造函数执行结束)先行发生于它的finalize方法的开始
8. A先行发生于B，B先行发生于C，则A先行发生于C


### 线程安全的实现
1. 不可变
不可变的对象一定是线程安全的，不需要再采取任何的线程安全保障措施

不可变的类型：
- final修饰的基本数据类型
- String
- 枚举类型
- Number部分子类，如Long和Double等数值包装类型，BigInteger和BigDecimal等大数据类型

对于集合类型，可以使用Collections.unmodifiableXXX()方法来获取一个不可变的集合 
```java
public class ImmutableExample {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> unmodifiableMap = Collections.unmodifiableMap(map);
        unmodifiableMap.put("a", 1);
    }
}
Exception in thread "main" java.lang.UnsupportedOperationException
    at java.util.Collections$UnmodifiableMap.put(Collections.java:1457)
    at ImmutableExample.main(ImmutableExample.java:9)
```

2. 互斥同步
synchronized和ReentrantLock

3. 非阻塞同步
互斥同步(阻塞同步)最主要的问题是线程阻塞和唤醒带来的性能问题。互斥同步是一种悲观的并发策略，认为不去做正确的同步措施，那么肯定会出现问题

随着硬件指令集的发展，我们可以使用基于冲突检测的乐观并发策略：先进性操作，如果没有其他线程争用共享数据，那么操作就成功了，否则不断进行重试，直到成功为止

- CAS

乐观锁需要操作和冲突检测这两个步骤具备原子性，硬件支持的原子性操作主要是：CAS(Compare-and-swap)，CAS指令需要三个操作数，内存地址V，旧值A和新值B。当执行操作的时候，只有当V的值为A，才将V的值更新为B

- AtomicInteger
其调用了Unsafe类的CAS操作

```java
以下代码使用了 AtomicInteger 执行了自增的操作。

private AtomicInteger cnt = new AtomicInteger();

public void add() {
    cnt.incrementAndGet();
}
以下代码是 incrementAndGet() 的源码，它调用了 Unsafe 的 getAndAddInt() 。

public final int incrementAndGet() {
    return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
}
以下代码是 getAndAddInt() 源码，var1 指示对象内存地址，var2 指示该字段相对对象内存地址的偏移，var4 指示操作需要加的数值，这里为 1。通过 getIntVolatile(var1, var2) 得到旧的预期值，通过调用 compareAndSwapInt() 来进行 CAS 比较，如果该字段内存地址中的值等于 var5，那么就更新内存地址为 var1+var2 的变量为 var5+var4。

可以看到 getAndAddInt() 在一个循环中进行，发生冲突的做法是不断的进行重试。

public final int getAndAddInt(Object var1, long var2, int var4) {
    int var5;
    do {
        var5 = this.getIntVolatile(var1, var2);
    } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));

    return var5;
}
```

- ABA问题

如果一个变量初次读取的时候是A值，它的值被改为B，后来又改回为A，那么CAS操作就会误认为它从来没有被改变

J.U.C包提供了一个带有标记的原子引用类AtomicStampedReference来解决这个问题，它通过控制变量值的版本来保证CAS的正确性。大部分情况下ABA问题不会影响程序并发的正确性，如果需要解决ABA问题，建议使用传统的互斥同步

---
#### 不需要同步的方案
可以使用栈封闭或者线程本地存储
##### 栈封闭
多个线程访问同一个方法的局部变量的时候，不会出现线程安全问题，因为局部变量存储在虚拟机栈中，属于线程私有的，也就是说尽量不要使用全局变量，能将变量放在方法内成为局部变量最好

##### 线程本地存储
如果一段代码所需要的数据需要与其他代码共享，那么看这些共享数据能否保证在同一个线程中执行，如果可以把共享数据的可见范围限制在同一个线程之内，那么无需同步也能够保证线程之间不出现数据争用的问题

一个应用实例就是Web交互模式中的“一个请求对应一个服务器线程”的处理方式，可以使用线程本地存储来解决线程安全问题

可以使用java.lang.ThreadLocal类来实现线程本地存储功能
```java
public class ThreadLocalExample {
    public static void main(String[] args) {
        ThreadLocal threadLocal = new ThreadLocal();
        Thread thread1 = new Thread(() -> {
            threadLocal.set(1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadLocal.get());
            threadLocal.remove();
        });
        Thread thread2 = new Thread(() -> {
            threadLocal.set(2);
            threadLocal.remove();
        });
        thread1.start();
        thread2.start();
    }
}
输出的结果还是1
```

每个Thread都有一个ThreadLocal.ThreadLocalMap对象。当调用一个ThreadLocal的set方法时，先得到当前线程的ThreadLocalMap对象，然后将ThreadLocal->value键值对插入到该Map中

在一些场景(尤其是使用线程池)下，由于ThreadLocal.ThreadLocalMap的底层数据结构导致ThreadLocal有内存泄露的情况，应该在每次使用ThreadLocal后手动调用remove方法，以避免出现ThreadLocal经典的内存泄露

---
### JVM对synchronized的优化
#### 自旋锁
互斥同步进入阻塞状态的开销很大，应该尽量避免。自旋锁的思想是让一个线程在请求一个共享数据的锁时执行忙循环一段时间，如果这段时间内能够获得锁，就可以避免进入阻塞状态，使用于共享数据的锁定状态很短的场景

在JDK1.6中引入了自适应的自旋锁，自适应意味着自旋的次数不在固定了，而是由前一次在同一个锁上的自旋次数和锁的拥有者的状态来决定的

#### 锁消除
锁消除是对于被检测出不可能存在竞争的共享数据的锁进行消除

锁消除主要是通过逃逸分析来支持的，如果堆上的共享数据不可能逃逸出去被其他线程访问到，那么就可以把它们当成私有数据对待，可以把加在上面的锁消除掉。很多方法虽然看着没有加锁，但是可能隐式加了锁，比如String的concatString方法就自动加了锁

#### 锁粗化
如果一系列的连续操作都对同一对象反复加锁和解锁，频繁的加锁操作就会导致性能损耗

比如String的append方法连续执行的话，虚拟机会检测到这样一串零碎的操作都对同一个对象加锁，将会把加锁的范围扩展(粗化)到整个操作序列的外部，使其只加一个锁就可以了

#### 轻量级锁
锁的四个状态：无锁状态(unlocked)、偏向锁状态(biasble)、轻量级锁状态(lightweight locked)和重量级锁状态(inflated)

HotSpot虚拟机对象头的数据被称为Mark Word

轻量级锁是相对于传统的重量级锁而言，它使用 CAS 操作来避免重量级锁使用互斥量的开销。对于绝大部分的锁，在整个同步周期内都是不存在竞争的，因此也就不需要都使用互斥量进行同步，可以先采用 CAS 操作进行同步，如果 CAS 失败了再改用互斥量进行同步。

当尝试获取一个锁对象时，如果锁对象标记为 0 01，说明锁对象的锁未锁定（unlocked）状态。此时虚拟机在当前线程的虚拟机栈中创建 Lock Record，然后使用 CAS 操作将对象的 Mark Word 更新为 Lock Record 指针。如果 CAS 操作成功了，那么线程就获取了该对象上的锁，并且对象的 Mark Word 的锁标记变为 00，表示该对象处于轻量级锁状态。

如果 CAS 操作失败了，虚拟机首先会检查对象的 Mark Word 是否指向当前线程的虚拟机栈，如果是的话说明当前线程已经拥有了这个锁对象，那就可以直接进入同步块继续执行，否则说明这个锁对象已经被其他线程线程抢占了。如果有两条以上的线程争用同一个锁，那轻量级锁就不再有效，要膨胀为重量级锁。

#### 偏向锁
偏向锁的思想是让第一个获取锁对象的线程，在后续获取该锁的时候不再需要进行同步操作，甚至连CAS操作也不需要

当锁对象第一次被线程获得的时候，进入偏向状态，标记为101，同时使用CAS操作将线程ID记录到Mark Word中，如果CAS操作成功，这个线程以后每次进入这个锁相关的同步块就不需要进行任何的同步操作

当有另外的线程去尝试获取锁对象的，偏向状态就结束，偏向状态会变为未锁定状态或者轻量级锁状态

### 一些实践
- 有意义的线程名字，方便调试
- 缩小同步范围，减少锁争用。比如对于synchronized，应该尽量使用同步块而不是同步方法
- 多用同步工具少用wait()和notify()。CountDownLatch，CyclicBarrier，Semaphore和Exchanger这些同步类简化了编码操作
- 使用BlockingQueue实现生产者消费者问题
- 多用并发集合少用同步集合，例如应该使用ConcurrentHashMap而不是HashTable
- 使用本地变量和不可变类来保证线程安全
- 使用线程池而不是创建线程，创建线程代价比较高，线程池可以用有限的的线程来启动任务