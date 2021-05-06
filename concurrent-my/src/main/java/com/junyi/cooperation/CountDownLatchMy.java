package com.junyi.cooperation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * CountDownLatch 主要用于一个线程等待多个线程
 *
 * 使用 CountDownLatch 来协调线程
 * 1. 并行获取订单、派送单
 * 2. 检查差异性
 * 3. 保存差异性
 *
 * 还可以进一步优化的点是，第一步和第二、三步现在是串行关系，实际上可以并行执行，详见 CyclicBarrierMy
 * @time: 2021/4/29 9:26
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class CountDownLatchMy {

    public static final Executor executor = Executors.newFixedThreadPool(2);

    public void process() {

        ArrayList<String> list = new ArrayList<>();     // 对账订单
        while (!list.isEmpty()) {       // 还存在未对账的订单
            CountDownLatch cdl = new CountDownLatch(2);
            AtomicReference<String> order = null;       // 订单
            AtomicReference<String> deliver = null;     // 派送单
            executor.execute(() -> {
                order.set(getOrder());
                cdl.countDown();
            });
            executor.execute(() -> {
                deliver.set(getDeliver());
                cdl.countDown();
            });
            try {
                cdl.await();    // 等待获取订单和派送单
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            check();
            saveDiff();
        }

    }

    /** 检查是否存在差异 */
    private void check() {

    }

    /** 保存差异 */
    private void saveDiff() {

    }

    /** 获取1个派送单 */
    private String getDeliver() {
        return "";
    }

    /** 获取1个订单 */
    private String getOrder() {
        return "";
    }

}
