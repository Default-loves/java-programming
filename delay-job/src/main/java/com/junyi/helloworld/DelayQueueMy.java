package com.junyi.helloworld;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2020/12/19 11:50
 * @version: 1.0
 * @author: junyi Xu
 * @description: 使用 concurrent 包下面的 DelayQueue
 * @see java.util.concurrent.DelayQueue
 */
public class DelayQueueMy {

    @Test
    public void testResult() throws InterruptedException {

        // 新建3个任务，并依次设置超时时间为 3s 10s 6s
        DelayTask d1 = new DelayTask(1, System.currentTimeMillis() + 3000L);
        DelayTask d2 = new DelayTask(2, System.currentTimeMillis() + 10000L);
        DelayTask d3 = new DelayTask(3, System.currentTimeMillis() + 6000L);

        java.util.concurrent.DelayQueue<DelayTask> queue = new java.util.concurrent.DelayQueue<>();
        queue.add(d1);
        queue.add(d2);
        queue.add(d3);
        int size = queue.size();

        System.out.println("当前时间是：" + LocalDateTime.now());

        // 从延时队列中获取元素， 将输出 d2 、d1 、d3
        for (int i = 0; i < size; i++) {
            System.out.println(queue.take() + " ------ " + LocalDateTime.now());
        }
    }


    class DelayTask implements Delayed {

        private Integer taskId;

        private long exeTime;

        DelayTask(Integer taskId, long exeTime) {
            this.taskId = taskId;
            this.exeTime = exeTime;
        }

        /** 计算还有多久到期 */
        @Override
        public long getDelay(TimeUnit unit) {
            return exeTime - System.currentTimeMillis();
        }

        @Override
        public int compareTo(Delayed o) {
            DelayTask t = (DelayTask) o;
            if (this.exeTime - t.exeTime <= 0) {
                return -1;
            } else {
                return 1;
            }
        }

        @Override
        public String toString() {
            return "DelayTask{" +
                    "taskId=" + taskId +
                    ", exeTime=" + exeTime +
                    '}';
        }
    }
}