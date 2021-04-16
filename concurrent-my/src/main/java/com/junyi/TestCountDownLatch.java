package com.junyi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @time: 2021/4/9 16:57
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class TestCountDownLatch {

    private static CountDownLatch cdl = new CountDownLatch(1);
    @Test
    public void testCountDownLatch() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(() -> {
            try {
                Thread.sleep(3_000);
                log.info("start");
                cdl.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executor.execute(() -> {
            try {
                cdl.await();
                log.info("end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread.currentThread().join();
    }
}
