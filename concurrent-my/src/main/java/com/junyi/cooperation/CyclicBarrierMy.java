package com.junyi.cooperation;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier 主要用于线程之间相互等待
 * CyclicBarrier 的计数器变为0后会自动执行当中的方法，然后将值重置为设定值
 * @time: 2021/4/29 9:42
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class CyclicBarrierMy {

    public static List<String> uncheckList;     // 未检查的订单
    public static Vector<String> orderList;     // 订单
    public static Vector<String> deliverList;   // 派送单
    public static Executor executor = Executors.newFixedThreadPool(1);
    public CyclicBarrier cb = new CyclicBarrier(2, () -> {
        executor.execute(() -> checkAndSave());
    });

    public void checkAndSave() {
        String order = orderList.remove(0);
        String deliver = deliverList.remove(0);
        // 检查差异
        // 保存差异
    }


    public void process() {
        while (!uncheckList.isEmpty()) {
            new Thread(() -> {
                try {
                    orderList.add(getOrder());
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
            new Thread(() ->  {
                try {
                    deliverList.add(getDeliver());
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /** 获取订单 */
    private String getOrder() {
        return null;
    }

    /** 获取派送单 */
    private String getDeliver() {
        return null;
    }
}
