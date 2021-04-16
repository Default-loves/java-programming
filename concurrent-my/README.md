设计模式：Guarded Suspension（保护性暂停）

并发编程领域里，解决分工问题也有一系列的设计模式，比较常用的主要有 Thread-Per-Message 模式、Worker Thread 模式、生产者 - 消费者模式





在Java开发中，解决并发问题的核心技术管程，管程技术的实现就是synchronized



CompletableFuture默认使用的是ForkJoinPool公共线程池，这个线程池默认的线程数量是CPU的核数，为了避免业务之间互相干扰，使用CompletableFuture的时候，最好指定自己创建的线程池，比如：

```java
ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
    3, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
CompletableFuture.runAsync(()-> System.out.println("hello"), threadPoolExecutor);
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

// 执行完会执行，对应 finally
// 无返回值
CompletionStage<R> whenComplete(consumer);
CompletionStage<R> whenCompleteAsync(consumer);
// 有返回值
CompletionStage<R> handle(fn);
CompletionStage<R> handleAsync(fn);
````

