
java.util.concurrent包中的内容


* [J.U.C(java.util.concurrent)](#J.U.C(java.util.concurrent))
    * [ReadWriteLock](#readwritelock)
    * [StampedLock](#stampedlock)
	* [CountDownLatch](#countdownlatch)
	* [CyclicBarier](#cyclicbarier)
	* [Semaphore](#semaphore)
* [J.U.C-其他组件](#J.U.C-其他组件)
    * [Future](#future)
	* [FutreTask](#futretask)
	* [BlockingQueue](#blockingqueue)
	* [ForkJoin](#forkjoin)
* [Concurrent集合](#concurrent集合)
* [Atomic](#atomic)
* [ThreadLocal](#threadlocal)

### J.U.C(java.util.concurrent)

#### ReadWriteLock
ReadWriteLock只允许一个线程写入；

ReadWriteLock允许多个线程在没有写入时同时读取；

ReadWriteLock适合读多写少的场景。
```java
public class Counter {
    private final ReadWriteLock rwlock = new ReentrantReadWriteLock();
    private final Lock rlock = rwlock.readLock();
    private final Lock wlock = rwlock.writeLock();
    private int[] counts = new int[10];

    public void inc(int index) {
        wlock.lock(); // 加写锁
        try {
            counts[index] += 1;
        } finally {
            wlock.unlock(); // 释放写锁
        }
    }

    public int[] get() {
        rlock.lock(); // 加读锁
        try {
            return Arrays.copyOf(counts, counts.length);
        } finally {
            rlock.unlock(); // 释放读锁
        }
    }
}
```

#### StampedLock
StampedLock提供了乐观读锁，可取代ReadWriteLock以进一步提升并发性能

ReadWriteLock，会发现它有个潜在的问题：如果有线程正在读，写线程需要等待读线程释放锁后才能获取写锁，即读的过程中不允许写，这是一种悲观的读锁。

StampedLock和ReadWriteLock相比，改进之处在于：读的过程中也允许获取写锁后写入！因此，我们读的数据就可能不一致，所以，需要一点额外的代码来判断读的过程中是否有写入，这种读锁是一种乐观锁。
```java
public class Point {
    private final StampedLock stampedLock = new StampedLock();

    private double x;
    private double y;

    public void move(double deltaX, double deltaY) {
        long stamp = stampedLock.writeLock(); // 获取写锁
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            stampedLock.unlockWrite(stamp); // 释放写锁
        }
    }

    public double distanceFromOrigin() {
        long stamp = stampedLock.tryOptimisticRead(); // 获得一个乐观读锁
        // 注意下面两行代码不是原子操作
        // 假设x,y = (100,200)
        double currentX = x;
        // 此处已读取到x=100，但x,y可能被写线程修改为(300,400)
        double currentY = y;
        // 此处已读取到y，如果没有写入，读取是正确的(100,200)
        // 如果有写入，读取是错误的(100,400)
        if (!stampedLock.validate(stamp)) { // 检查乐观读锁后是否有其他写锁发生
            stamp = stampedLock.readLock(); // 获取一个悲观读锁
            try {
                currentX = x;
                currentY = y;
            } finally {
                stampedLock.unlockRead(stamp); // 释放悲观读锁
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}
```


#### CountDownLatch
用来控制一个或者多个线程等待多个线程，每次调用CountDownLatch对象的countDown方法会将计数器减一，减到0的时候会唤醒因为调用await方法而等待的线程

CountDownLatch是一次性的，使用完后不能再次使用
```java
//简单演示CountDownLatch效果
public static class CountDownLatchExample {
        public void func() throws InterruptedException {
            int totalThread = 10;
            CountDownLatch countDownLatch = new CountDownLatch(totalThread);
            ExecutorService executorService = Executors.newCachedThreadPool();
            for (int i = 0; i < totalThread; i++) {
                executorService.execute(() -> {
                    System.out.print("run..");
                    countDownLatch.countDown();
                });
            }
            countDownLatch.await();
            System.out.println("End");
            executorService.shutdown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchExample ce = new CountDownLatchExample();
        ce.func();
    }
/*
run..run..run..run..run..run..run..run..run..run..End
*/
```

#### CyclicBarrier
控制多个线程互相等待，只有当多个线程都到达的时候，唤醒所有到达"屏障"的线程并行执行

线程调用await()方法后计数器会减1，并进行等待，直到计数器为0，所有调用await()方法而在等待的线程才能继续执行

CyclicBarrier和CountdownLatch的一个区别是，CyclicBarrier在计数器减到0的时候会通过reset()方法重置计数器，可以再次使用，因此也叫做循环屏障

```java
//简单演示CyclicBarier效果
public static class CyclicBarrierExample {
        public void func() {
            int totalThread = 5;
            CyclicBarrier cyclicBarrier = new CyclicBarrier(totalThread);
            ExecutorService executorService = Executors.newCachedThreadPool();
            for (int i = 0; i < totalThread; i++) {
                executorService.execute(() -> {
                    System.out.print("before..");
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.print("after..");
                });
            }
            executorService.shutdown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrierExample ce = new CyclicBarrierExample();
        ce.func();
    }
```

```java
//等待坐车，一次进来5个人，等这5个人坐车出发，再放进来5个人
public class CyclicBarrierSample {
  public static void main(String[] args) throws InterruptedException {
      CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {     //当从5减到0后会调用这个回调方法
          @Override
          public void run() {
              System.out.println("Action...GO again!");
          }
      });
      for (int i = 0; i < 5; i++) {
          Thread t = new Thread(new CyclicWorker(barrier));
          t.start();
      }
  }
  static class CyclicWorker implements Runnable {
      private CyclicBarrier barrier;
      public CyclicWorker(CyclicBarrier barrier) {
          this.barrier = barrier;
      }
      @Override
      public void run() {
          try {
              for (int i=0; i<3 ; i++){
                  System.out.println("Executed!");
                  barrier.await();
              }
          } catch (BrokenBarrierException e) {
              e.printStackTrace();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
  }
}
```

#### Semaphore
类似于操作系统中的信号量，可以控制对互斥资源的访问线程数
```java
//以下代码模拟了对某个服务的并发请求，总共有10个请求，每次只能有3个客户端同时访问
public static class SemaphoreExample {
        public void func() {
            int numClient = 3;
            int numRequest = 10;
            Semaphore semaphore = new Semaphore(numClient);
            ExecutorService executorService = Executors.newCachedThreadPool();
            for (int i = 0; i < numRequest; i++) {
                executorService.execute(() -> {
                    try {
                        semaphore.acquire();
                        System.out.print(semaphore.availablePermits() + " ");   //程序每次输出都是不一样的，这个无法控制
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    semaphore.release();
                });
            }
            executorService.shutdown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SemaphoreExample ce = new SemaphoreExample();
        ce.func();
    }
```

```java
//等待坐车，一次只进来5个人，等这5个人坐车出发，再放进来5个人
import java.util.concurrent.Semaphore;
public class AbnormalSemaphoreSample {
  public static void main(String[] args) throws InterruptedException {
      Semaphore semaphore = new Semaphore(0);
      for (int i = 0; i < 10; i++) {
          Thread t = new Thread(new MyWorker(semaphore));
          t.start();
      }
      System.out.println("Action...GO!");
      semaphore.release(5);
      System.out.println("Wait for permits off");
      while (semaphore.availablePermits()!=0) {
          Thread.sleep(100L);
      }
      System.out.println("Action...GO again!");
      semaphore.release(5);
  }
}
class MyWorker implements Runnable {
  private Semaphore semaphore;
  public MyWorker(Semaphore semaphore) {
      this.semaphore = semaphore;
  }
  @Override
  public void run() {
      try {
          semaphore.acquire();
          System.out.println("Executed!");
      } catch (InterruptedException e) {
          e.printStackTrace();
      }
  }
}
```


### J.U.C-其他组件

#### Future
通过Future可以获得callable线程提交执行后的结果
```java
ExecutorService executor = Executors.newFixedThreadPool(4); 
// 定义任务:
Callable<String> task = new Task();
// 提交任务并获得Future:
Future<String> future = executor.submit(task);
// 从Future获取异步执行返回的结果:
String result = future.get(); // 可能阻塞
```

#### CompletableFuture
Future获取结果的方法get()会阻塞主线程，为此我们可以使用CompletableFuture异步回调
```java
public class Main {
    public static void main(String[] args) throws Exception {
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(Main::fetchPrice);
        // 如果执行成功:
        cf.thenAccept((result) -> {
            System.out.println("price: " + result);
        });
        // 如果执行异常:
        cf.exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200);
    }

    static Double fetchPrice() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        if (Math.random() < 0.3) {
            throw new RuntimeException("fetch price failed!");
        }
        return 5 + Math.random() * 20;
    }

```

CompletableFuture还可以实现串行操作，thenApplyAsync()用于串行化另一个CompletableFuture；
```java
public class Main {
    public static void main(String[] args) throws Exception {
        // 第一个任务:
        CompletableFuture<String> cfQuery = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油");
        });
        // cfQuery成功后继续执行下一个任务:
        CompletableFuture<Double> cfFetch = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice(code);
        });
        // cfFetch成功后打印结果:
        cfFetch.thenAccept((result) -> {
            System.out.println("price: " + result);
        });
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(2000);
    }

    static String queryCode(String name) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        return "601857";
    }

    static Double fetchPrice(String code) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        return 5 + Math.random() * 20;
    }
}
```

CompletableFuture还可以并行执行
- anyOf()可以实现“任意个CompletableFuture只要一个成功”
- allOf()可以实现“所有CompletableFuture都必须成功”
```java
public class Main {
    public static void main(String[] args) throws Exception {
        // 两个CompletableFuture执行异步查询:
        CompletableFuture<String> cfQueryFromSina = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油", "https://finance.sina.com.cn/code/");
        });
        CompletableFuture<String> cfQueryFrom163 = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油", "https://money.163.com/code/");
        });

        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfQuery = CompletableFuture.anyOf(cfQueryFromSina, cfQueryFrom163);

        // 两个CompletableFuture执行异步查询:
        CompletableFuture<Double> cfFetchFromSina = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://finance.sina.com.cn/price/");
        });
        CompletableFuture<Double> cfFetchFrom163 = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://money.163.com/price/");
        });

        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfFetch = CompletableFuture.anyOf(cfFetchFromSina, cfFetchFrom163);

        // 最终结果:
        cfFetch.thenAccept((result) -> {
            System.out.println("price: " + result);
        });
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200);
    }

    static String queryCode(String name, String url) {
        System.out.println("query code from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return "601857";
    }

    static Double fetchPrice(String code, String url) {
        System.out.println("query price from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return 5 + Math.random() * 20;
    }
}

```

#### FutreTask
FutureTask实现了RunnableFuture接口

FutureTask主要用于异步获取一个需要计算时间比较长的线程的结果，用FutureTask来封装这个任务，主线程在完成自己的任务后再去获取结果

```java
public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int result = 0;
                for (int i = 0; i < 100; i++) {
                    Thread.sleep(10);
                    result += i;
                }
                return result;
            }
        });
        Thread thread = new Thread(futureTask);
        thread.start();

        Thread otherThread = new Thread(() -> {
            System.out.println("Other thread is running");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        otherThread.start();
        System.out.println("End");
        System.out.println(futureTask.get());
    }
```
#### 并发队列
JDK 并发队列按照实现方式可以分为阻塞队列和非阻塞队列两种类型，阻塞队列是基于锁实现的，非阻塞队列是基于 CAS 操作实现的

阻塞队列实现了BlockingQueue接口

##### 非阻塞队列
分阻塞队列不使用加锁阻塞线程，而是使用CAS操作来实现并发操作，性能更好

ConcurrentLinkedQueue，它是一个采用双向链表实现的无界并发非阻塞队列，它属于 LinkedQueue 的安全版本。ConcurrentLinkedQueue 内部采用 CAS 操作保证线程安全，这是非阻塞队列实现的基础，相比 ArrayBlockingQueue、LinkedBlockingQueue 具备较高的性能。

ConcurrentLinkedDeque，也是一种采用双向链表结构的无界并发非阻塞队列。与 ConcurrentLinkedQueue 不同的是，ConcurrentLinkedDeque 属于双端队列，它同时支持 FIFO 和 FILO 两种模式，可以从队列的头部插入和删除数据，也可以从队列尾部插入和删除数据，适用于多生产者和多消费者的场景。

##### 阻塞队列
阻塞队列自身是线程安全的，基于锁实现多线程并发

java.util.concurrent.BlockingQueue接口有以下阻塞队列的实现：
- 优先级队列：PriorityBlockingQueue
- FIFO队列：LinkedBlockingQueue、ArrayBlockingQueue(固定长度)

提供了阻塞的take()方法和put()方法，如果队列为空调用take方法会阻塞，直到队列中有内容；如果队列满了调用put方法会阻塞，直到队列有空闲的位置
```java
//以下代码实现生产者消费者问题
public static class ProductConsumer {
        private static BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
        public static class Product extends Thread{
            public void run() {
                try {
                    queue.put("Product");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("Product..");
            }
        }

        public static class Consumer extends Thread {
            public void run() {
                try {
                    String product = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("Consume..");
            }
        }

        public static void main(String[] args) {
            for (int i = 0; i < 2; i++) {
                Product product = new Product();
                product.start();
            }
            for (int i = 0; i < 5; i++) {
                Consumer consumer = new Consumer();
                consumer.start();
            }
            for (int i = 0; i < 3; i++) {
                Product product = new Product();
                product.start();
            }
        }
    }
```

#### ForkJoin
主要用于并行计算，和MapReduce原理类似，都是把大的计算任务拆分成多个小任务并行计算

ForkJoin使用ForkJoinPool来启动，它是一个特殊的线程池，线程数量取决于CPU核数

```java
//例子1
public static class ForkJoinExample extends RecursiveTask<Integer> {
        private final int threadhold = 5;
        private int first;
        private int last;

        public ForkJoinExample(int first, int last) {
            this.first = first;
            this.last = last;
        }

        @Override
        protected Integer compute() {
            int result = 0;
            if (last - first < threadhold) {
                for (int i = first; i < last; i++) {
                    result += i;
                }
            } else {
                int mid = first + (last - first) / 2;
                ForkJoinExample left = new ForkJoinExample(first, mid);
                ForkJoinExample right = new ForkJoinExample(mid+1, last);
                left.fork();
                right.fork();
                result = left.join() + right.join();
            }
            return result;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinExample fe = new ForkJoinExample(0, 10000);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future future = forkJoinPool.submit(fe);
        System.out.println(future.get());
    }

//例子2
public class Main {
    public static void main(String[] args) throws Exception {
        // 创建2000个随机数组成的数组:
        long[] array = new long[2000];
        long expectedSum = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = random();
            expectedSum += array[i];
        }
        System.out.println("Expected sum: " + expectedSum);
        // fork/join:
        ForkJoinTask<Long> task = new SumTask(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = ForkJoinPool.commonPool().invoke(task);
        long endTime = System.currentTimeMillis();
        System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
    }

    static Random random = new Random(0);

    static long random() {
        return random.nextInt(10000);
    }
}

class SumTask extends RecursiveTask<Long> {
    static final int THRESHOLD = 500;
    long[] array;
    int start;
    int end;

    SumTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            // 如果任务足够小,直接计算:
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += this.array[i];
                // 故意放慢计算速度:
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
            }
            return sum;
        }
        // 任务太大,一分为二:
        int middle = (end + start) / 2;
        System.out.println(String.format("split %d~%d ==> %d~%d, %d~%d", start, end, start, middle, middle, end));
        SumTask subtask1 = new SumTask(this.array, start, middle);
        SumTask subtask2 = new SumTask(this.array, middle, end);
        invokeAll(subtask1, subtask2);
        Long subresult1 = subtask1.join();
        Long subresult2 = subtask2.join();
        Long result = subresult1 + subresult2;
        System.out.println("result = " + subresult1 + " + " + subresult2 + " ==> " + result);
        return result;
    }
}

```

ForkJoinPool实现了工作窃取算法来提高CPU的利用率，每个线程都维护了一个双端队列，用来存储需要执行的任务。工作窃取算法允许空闲的线程窃取另一个线程的任务，窃取的任务必须是最晚的任务，避免和队列所属线程发生竞争，但是如果队列中只有一个任务的时候还是会发生竞争的

### Concurrent集合
Concurrent包下为我们提供了线程安全的并发集合类，可以直接使用
| interface |     non-thread-safe     | thread-safe                              |
| :-------: | :---------------------: | ---------------------------------------- |
|   List    |        ArrayList        | CopyOnWriteArrayList                     |
|    Map    |         HashMap         | ConcurrentHashMap                        |
|    Set    |    HashSet / TreeSet    | CopyOnWriteArraySet                      |
|   Queue   | ArrayDeque / LinkedList | ArrayBlockingQueue / LinkedBlockingQueue |
|   Deque   | ArrayDeque / LinkedList | LinkedBlockingDeque                      |

### Atomic
Atomic类是通过无锁（lock-free）的方式实现的线程安全（thread-safe）访问。它的主要原理是利用了CAS：Compare and Set。

适用的场景：计数器、累加器等

以AtomicInteger为例，它提供的主要操作有：
- 增加值并返回新值：int addAndGet(int delta)
- 加1后返回新值：int incrementAndGet()
- 获取当前值：int get()
- 用CAS方式设置：int compareAndSet(int expect, int update)

### ThreadLocal
在一个线程中，横跨若干方法调用，需要传递的对象，我们通常称之为上下文（Context），它是一种状态，可以是用户身份、任务信息等。给每个方法增加一个context参数非常麻烦，而且有些时候，如果调用链有无法修改源码的第三方库，User对象就传不进去了。

Java标准库提供了一个特殊的ThreadLocal，它可以在一个线程中传递同一个对象，具体使用如下：
```java
public class UserContext implements AutoCloseable {

    static final ThreadLocal<String> ctx = new ThreadLocal<>();

    public UserContext(String user) {
        ctx.set(user);
    }

    public static String currentUser() {
        return ctx.get();
    }

    @Override
    public void close() {
        ctx.remove();
    }
}

void processUser(user) {
    try (var ctx = new UserContext("Bob")) {
        // 可任意调用UserContext.currentUser()，在移除之前，所有方法都可以随时获取到该实例
        String currentUser = UserContext.currentUser();
        step1();
        step2();
    } // 在此自动调用UserContext.close()方法释放ThreadLocal关联对象    

}
void step1() {
    String currentUser = UserContext.currentUser();
    log();
    printUser();
}

void log() {
    String currentUser = UserContext.currentUser();
    println(u.name);
}

void step2() {
    String currentUser = UserContext.currentUser();
    checkUser(u.id);
}

```

#### ThreadLocalRandom
ThreadLocalRandom 是 Java 7 引入的一个生成随机数的类。

使用：`ThreadLocalRandom.current().nextX(…) (where X is Int, Long, etc)`

> 在多线程环境下，每个线程要使用线程自己的实例，而不能将其设置为静态变量由多线程共享。在current方法中，会初始化seed，如果多线程共享，会导致seed重用



### synchronize 对比 ReentrantLock

ReentrantLock相比较于synchronize，能够提供以下的功能，这也是为什么已经有了synchronize还要弄一个ReentrantLock的原因

1. 能够响应中断。synchronized 的问题是，持有锁 A 后，如果尝试获取锁 B 失败，那么线程就进入阻塞状态，一旦发生死锁，就没有任何机会来唤醒阻塞的线程。但如果阻塞状态的线程能够响应中断信号，也就是说当我们给阻塞的线程发送中断信号的时候，能够唤醒它，那它就有机会释放曾经持有的锁 A。这样就破坏了不可抢占条件了。
2. 支持超时。如果线程在一段时间之内没有获取到锁，不是进入阻塞状态，而是返回一个错误，那这个线程也有机会释放曾经持有的锁。这样也能破坏不可抢占条件。
3. 非阻塞地获取锁。如果尝试获取锁失败，并不进入阻塞状态，而是直接返回，那这个线程也有机会释放曾经持有的锁。这样也能破坏不可抢占条件。