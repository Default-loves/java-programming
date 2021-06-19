package com.junyi.cases;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @time: 2021/5/11 9:21
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class RateLimiterMy {

    /** 使用guava中提供的限流器实现 */
    @Test
    public void test() {

        //限流器流速：2个请求/秒
        RateLimiter limiter = RateLimiter.create(2.0);
        //执行任务的线程池
        ExecutorService es = Executors.newFixedThreadPool(1);
        //记录上一次执行时间
        final long[] prev = {System.nanoTime()};
        //测试执行20次
        for (int i = 0; i < 20; i++) {
            //限流器限流
            limiter.acquire();
            //提交任务异步执行
            es.execute(() -> {
                long cur = System.nanoTime();
                //打印时间间隔：毫秒
                System.out.println(
                        (cur - prev[0]) / 1000_000);
                prev[0] = cur;
            });
        }
    }
}
