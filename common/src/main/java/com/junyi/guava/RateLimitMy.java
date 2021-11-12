package com.junyi.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2021/10/28 9:13
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class RateLimitMy {

    @Test
    public void test() {
        RateLimiter rateLimiter = RateLimiter.create(3.0);
        for (int i = 0; i < 10; i++) {
             if (rateLimiter.tryAcquire()) {
                 System.out.println(rateLimiter.getRate() + " | " + new Date());
             } else {
                 System.out.println("fail" + " | " + new Date());
             }
        }
    }

    @Test
    public void test2() {
        RateLimiter rateLimiter = RateLimiter.create(3.0);

        for (int i = 0; i < 10; i++) {
            rateLimiter.acquire();
            System.out.println(rateLimiter.getRate() + " | " + new Date());
        }
    }

    @Test
    public void test3() {
        RateLimiter rateLimiter = RateLimiter.create(3.0);

        for (int i = 0; i < 10; i++) {
            if (rateLimiter.tryAcquire(2, TimeUnit.SECONDS)) {
                System.out.println("ok" + " | " + new Date());
                doSomething();
            } else {
                System.out.println("fail" + " | " + new Date());
            }
        }
    }


    private void doSomething() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
