package com.junyi;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * 实现类似 Forking Cluster 的功能，即提供2个可用的节点，部署相同的服务，客户端同时发送给节点获取服务，只要客户端接收到其中1个，那么取消其余的任务
 * @time: 2021/3/29 10:47
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class ExecutorCompletionServiceMy2 {

    @Test
    public void test() throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(pool);
        ArrayList<Future<Integer>> futures = new ArrayList<>();
        futures.add(completionService.submit(() -> getInfo()));
        futures.add(completionService.submit(() -> getInfo()));

        Integer result;
        try {
            for (int i = 0; i < 2; i++) {
                result = completionService.take().get();
                if (result != null) {
                    break;
                }
            }
        } finally {
            for (Future<Integer> future : futures) {
                future.cancel(true);
            }
        }

    }

    private Integer getInfo() {
        return null;
    }
}
