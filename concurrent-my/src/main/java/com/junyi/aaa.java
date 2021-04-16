package com.junyi;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @time: 2020/10/26 17:13
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class aaa {

    static class ThreadId {
        static final AtomicLong
                nextId=new AtomicLong(0);
        //定义ThreadLocal变量
        static final ThreadLocal<Long>
                tl=ThreadLocal.withInitial(
                ()->nextId.getAndIncrement());
        //此方法会为每个线程分配一个唯一的Id
        static long get(){
            return tl.get();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println(ThreadId.get());
            System.out.println(ThreadId.get());
        }).start();
        Thread.sleep(1_000);
        new Thread(() -> {
            System.out.println(ThreadId.get());
            System.out.println(ThreadId.get());
        }).start();
        Thread.sleep(1_000);
        new Thread(() -> {
            System.out.println(ThreadId.get());
            System.out.println(ThreadId.get());
        }).start();

    }
}
