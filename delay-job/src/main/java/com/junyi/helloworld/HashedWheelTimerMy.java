package com.junyi.helloworld;

import io.netty.util.HashedWheelTimer;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2020/12/19 12:52
 * @version: 1.0
 * @author: junyi Xu
 * @description: 使用 Netty包的 HashedWheelTimerMy
 * 循环依次遍历每个格子的内容，执行符合条件的任务
 */
public class HashedWheelTimerMy {

    @Test
    public void test() throws InterruptedException {

        //设置每个格子是 100ms, 总共 256 个格子
        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(100, TimeUnit.MILLISECONDS, 256);
        CountDownLatch cdl = new CountDownLatch(3);
        //加入三个任务，依次设置超时时间是 10s 3s 6s

        System.out.println("加入一个任务，ID = 1, time= " + LocalDateTime.now());
        hashedWheelTimer.newTimeout(timeout -> {
            System.out.println("执行一个任务，ID = 1, time= " + LocalDateTime.now());
            cdl.countDown();
        }, 2, TimeUnit.SECONDS);

        System.out.println("加入一个任务，ID = 2, time= " + LocalDateTime.now());
        hashedWheelTimer.newTimeout(timeout -> {
            System.out.println("执行一个任务，ID = 2, time= " + LocalDateTime.now());
            cdl.countDown();
        }, 3, TimeUnit.SECONDS);

        System.out.println("加入一个任务，ID = 3, time= " + LocalDateTime.now());
        hashedWheelTimer.newTimeout(timeout -> {
            System.out.println("执行一个任务，ID = 3, time= " + LocalDateTime.now());
            cdl.countDown();
        }, 1, TimeUnit.SECONDS);

        System.out.println("等待任务执行===========");
        cdl.await(1, TimeUnit.MINUTES);
    }
}
