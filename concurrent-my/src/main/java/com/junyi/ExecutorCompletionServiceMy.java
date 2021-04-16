package com.junyi;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * 异步提交3个任务，返回结果将按照接收顺序添加到阻塞队列中，最后，获取阻塞队列的消息进行处理
 * @time: 2021/3/29 10:40
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 * @see: ExecutorCompletionService
 */
public class ExecutorCompletionServiceMy {

    @Test
    public void test() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ExecutorCompletionService<Integer> cs = new ExecutorCompletionService(executorService);
        cs.submit(() -> getInfo1());
        cs.submit(() -> getInfo2());
        cs.submit(() -> getInfo3());
        for (int i = 0; i < 3; i++) {
            Integer info = cs.take().get();
            executorService.submit(() -> save(info));
        }
    }

    private Integer getInfo1() {
        return null;
    }
    private Integer getInfo2() {
        return null;
    }

    private Integer getInfo3() {
        return null;
    }


    private void save(Integer info) {
        // 保存数据
    }
}
