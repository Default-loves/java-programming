









### 并发总览

Java并发编程总体来看可以分为三个问题：分工、协作、互斥

- 分工：使用线程池实现多个任务并行执行
- 协作：多个任务存在串行关系、并行关系等，线程需要阻塞，满足条件后再次执行
- 互斥：共享变量在同一时间只能被一个线程访问，需要保证数据的一致性和正确性

在Java开发中，解决并发问题的核心技术是管程，管程技术的实现就是synchronized

### 设计模式

设计模式：Guarded Suspension（保护性暂停）

并发编程领域里，解决分工问题也有一系列的设计模式，比较常用的主要有 Thread-Per-Message 模式、Worker Thread 模式、生产者 - 消费者模式

### 并发问题的来源

并发问题的产生，很大程度上是因为缓存带来的可见性问题，编译优化带来的有序型问题，线程切换带来的原子性问题等

**缓存带来的可见性问题**

线程在CPU执行的时候，会将变量从内存放置在缓存中以加速访问，对于多核CPU，就会导致多个线程修改共享变量数据不一致的问题

比如现在有两个线程t1和t2都加载了变量a到自己CPU核的缓存中

t1将变量a加1，此时t1所在核的缓存中变量a数值有变更，但是内存中的变量a仍然是旧值，t2所在核的缓存中的变量a还是旧值，导致数据不一致

**编译优化带来的有序性问题**

比如new操作，默认的顺序是：

1. 在内存中获取一块内存
2. 在内存上初始化对象
3. 将内存的地址赋值给变量

而编译优化后的顺序是：

1. 在内存中获取一块内存
2. 将内存的地址赋值给变量
3. 在内存上初始化对象

在多线程场景下，会导致获取到的对象没有初始化，进而产生异常

### 线程数

首先，计算机执行的任务可以分为两类：CPU密集型和IO密集型。

- CPU密集型：最佳线程数为CPU核心数+1。线程数和核心数接近，避免线程切换的成本
- IO密集型：最佳线程数为CPU 核数 * [ 1 +（I/O 耗时 / CPU 耗时）]。

### CompleteFuture

CompletableFuture默认使用的是ForkJoinPool公共线程池，这个线程池默认的线程数量是CPU的核数，为了避免业务之间互相干扰，使用CompletableFuture的时候，最好指定自己创建的线程池，比如：

```java
ForkJoinPool forkJoinPool = new ForkJoinPool(3);
CompletableFuture.runAsync(()-> System.out.println("hello"), forkJoinPool);
```

#### 串行关系

```java

// 有返回值
CompletionStage<R> thenApply(fn);
CompletionStage<R> thenApplyAsync(fn);
// 无返回值
CompletionStage<Void> thenAccept(consumer);
CompletionStage<Void> thenAcceptAsync(consumer);
CompletionStage<Void> thenRun(action);
CompletionStage<Void> thenRunAsync(action);
// 有返回值
CompletionStage<R> thenCompose(fn);
CompletionStage<R> thenComposeAsync(fn);
```

#### And汇聚关系

````java
CompletionStage<R> thenCombine(other, fn);
CompletionStage<R> thenCombineAsync(other, fn);
CompletionStage<Void> thenAcceptBoth(other, consumer);
CompletionStage<Void> thenAcceptBothAsync(other, consumer);
CompletionStage<Void> runAfterBoth(other, action);
CompletionStage<Void> runAfterBothAsync(other, action);
````

#### Or汇聚关系

```java
CompletionStage applyToEither(other, fn);
CompletionStage applyToEitherAsync(other, fn);
CompletionStage acceptEither(other, consumer);
CompletionStage acceptEitherAsync(other, consumer);
CompletionStage runAfterEither(other, action);
CompletionStage runAfterEitherAsync(other, action);
```

#### 异常处理

````java
// 报错会执行，对应 catch
CompletionStage exceptionally(fn);

// 最终总会执行的代码块，相当于 finally
// 无返回值
CompletionStage<R> whenComplete(consumer);
CompletionStage<R> whenCompleteAsync(consumer);
// 有返回值
CompletionStage<R> handle(fn);
CompletionStage<R> handleAsync(fn);
````



### 一些例子

高并发导致的死循环问题，or的赋值在while循环外面，当rf.compareAndSet(or, nr)=false的时候，会导致死循环，修改很简单，将or的赋值放在while循环里面即可

```java
final AtomicReference rf = new AtomicReference<>( new WMRange(0,0) );
WMRange or=rf.get();
do{ 
    // 检查参数合法性 
    if(v < or.lower){ 
        throw new IllegalArgumentException(); 
    } 
    nr = new WMRange(v, or.lower); 
}while(!rf.compareAndSet(or, nr));
```

```java
# 正确
do{ 
    or = rf.get();
    // 检查参数合法性 
    if(v < or.lower){ 
        throw new IllegalArgumentException(); 
    } 
    nr = new WMRange(v, or.lower); 
}while(!rf.compareAndSet(or, nr));    
```

共享线程池的问题。下面的代码findRuleByJdbc方法是一个阻塞IO，而且CompletableFuture使用的是公共的ForkJoinPool，容易导致整个ForkJoinPool的线程都阻塞

正确的做法是另外创建一个ForkJoinPool线程池，将任务隔离开而不互相影响

```java
//采购订单
PurchersOrder po;
CompletableFuture<Boolean> cf = 
  CompletableFuture.supplyAsync(()->{
    //在数据库中查询规则
    return findRuleByJdbc();
  }).thenApply(r -> {
    //规则校验
    return check(po, r);
});
Boolean isOk = cf.join();
```

