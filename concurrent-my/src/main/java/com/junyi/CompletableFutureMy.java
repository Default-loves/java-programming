package com.junyi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 异步编程的示例 <p/>
 * CompletableFuture 的使用
 * @time: 2020/10/26 16:53
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 * @see CompletableFuture
 */
@Slf4j
public class CompletableFutureMy {
    /**
     * 无返回值
     */
    @Test
    public void asyncThread()throws Exception{
        CompletableFuture async1 = CompletableFuture.runAsync(()->{
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName());
                System.out.println("none return Async");
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        // 调用get()将等待异步逻辑处理完成，即会阻塞
        async1.get();
        log.info("End");
    }

    @Test
    public void testThenRun() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
            log.info("start");
            this.sleep(3);
            log.info("mission 1");
        });
        CompletableFuture<Void> f2 = f1.thenRun(() -> {
            log.info("mission 1 finish");
        });
        f2.get();
        log.info("end");
    }

    /**
     * 有返回值
     */
    @Test
    public void asyncThread2()throws Exception{
        CompletableFuture<String> async2 = CompletableFuture.supplyAsync(()->{
            log.info("inside");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        });
        String result = async2.get();
        // 支持设置超时时间，如果超时会报错
//         String result = async2.get(1L, TimeUnit.SECONDS);
        log.info(result);
    }

    /**
     * 等待多个任务全部执行完毕，效果类似于 CountDownLatch 和 CyclicBarrier
     * @throws Exception
     */
    @Test
    public void asyncThread3()throws Exception{
        CompletableFuture<String> a = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        });
        CompletableFuture<String> b = CompletableFuture.supplyAsync(() -> "youth");
        CompletableFuture<String> c = CompletableFuture.supplyAsync(() -> "!");

        CompletableFuture all = CompletableFuture.allOf(a,b,c);
        all.get();

        String result2 = Stream.of(a, b, c)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));
        log.info(result2);
    }

    /**
     * 等待多个任务执行，只接收最先完成的任务
     * @throws Exception
     */
    @Test
    public void asyncThread4() throws Exception{
        CompletableFuture<String> a = CompletableFuture.supplyAsync(() ->{
            try{
                Thread.sleep(1000);
                return "hello";
            }catch (Exception e) {
                e.printStackTrace();
                return "none~";
            }
        });
        CompletableFuture<String> b = CompletableFuture.supplyAsync(() -> "youth");
        CompletableFuture<String> c = CompletableFuture.supplyAsync(() -> "!");

        CompletableFuture<Object> any = CompletableFuture.anyOf(a,b,c);
        String result = (String)any.get();

        System.out.println(result);
    }

    /** or 关系的汇集 */
    @Test
    public void TestApplyToEither() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            this.sleep(3);
            System.out.println("mission 1");
            return "1";
        });
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            this.sleep(2);
            System.out.println("mission 2");
            return "2";
        });
        CompletableFuture<String> f3 = f1.applyToEither(f2, s -> s);
        System.out.println(f3.join());
        System.out.println("end");

    }



    /** and 关系的汇集任务 */
    @Test
    public void testThenCombine() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            this.sleep(3);
            System.out.println("mission 1");
            return "1";
        });
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            this.sleep(2);
            System.out.println("mission 2");
            return "2";
        });
        CompletableFuture f3 = f1.thenCombine(f2, (a, b) -> a + b);
        log.info(f3.join().toString());
        log.info("end");
    }

    /** 异常处理 */
    @Test
    public void testException() {
        CompletableFuture<Object> f1 = CompletableFuture.supplyAsync(() -> {
            log.info("Error example");
            throw new RuntimeException();
        }).exceptionally(e -> {
            log.info("handle error: {}", e.getMessage());
            return "0";
        });
        log.info(f1.join().toString());

    }



    private void sleep(Integer second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
