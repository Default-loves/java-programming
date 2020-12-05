package com.junyi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @time: 2020/10/26 16:53
 * @version: 1.0
 * @author: junyi Xu
 * @description: CompletableFuture 的使用
 * @see CompletableFuture
 */
@Slf4j
public class AsyncMy {
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
        // 调用get()将等待异步逻辑处理完成
        async1.get();
        log.info("End");
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
    public void asyncThread4()throws Exception{
        CompletableFuture<String> a = CompletableFuture.supplyAsync(() ->{
            try{
                Thread.sleep(1000);
                return "hello";
            }catch (Exception e){
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
}
