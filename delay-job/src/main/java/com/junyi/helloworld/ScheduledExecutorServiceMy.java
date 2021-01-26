package com.junyi.helloworld;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @time: 2021/1/7 18:23
 * @version: 1.0
 * @author: junyi Xu
 * @description: 使用 ScheduledExecutorService 执行定时任务
 */
@Slf4j
public class ScheduledExecutorServiceMy {

    public static Integer cnt = 0;

    @Test
    public void test() throws InterruptedException {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
        service.scheduleAtFixedRate(new TaskMy(), 10_000L, 3_000L, TimeUnit.MILLISECONDS);
        Thread.sleep(10_000_000L);
    }

    class TaskMy implements Runnable {
        @Override
        public void run() {
            log.info("hello: {}", cnt++);
        }
    }


}
