package com.junyi;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2021/4/19 10:58
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class ThreadPoolExecutorMy {

    @Test
    public void test() {

        ExecutorService es = new ThreadPoolExecutor(3, 10, 60L, TimeUnit.SECONDS,
                //注意要创建有界队列
                new LinkedBlockingQueue<Runnable>(1000),
                //建议根据业务需求实现ThreadFactory
                r-> new Thread(r, "echo-"+ r.hashCode()),
                //建议根据业务需求实现RejectedExecutionHandler
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
