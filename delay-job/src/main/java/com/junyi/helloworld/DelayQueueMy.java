package com.junyi.helloworld;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ScheduledExecutorService;
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
        DelayQueue<DelayTask> queue = new DelayQueue<>();
        long now = System.currentTimeMillis();
        queue.add(new DelayTask(1, now + 3000L));
        queue.add(new DelayTask(2, now + 10000L));
        queue.add(new DelayTask(3, now + 6000L));

        System.out.println("当前时间是：" + LocalDateTime.now());

        // 从延时队列中获取元素
        for (int i = 0; i < queue.size(); i++) {
            System.out.println(queue.take() + " ------ " + LocalDateTime.now());       // 将输出 d2 、d1 、d3
        }
    }


    class DelayTask implements Delayed {

        private Integer taskId;

        private long exeTime;

        DelayTask(Integer taskId, long exeTime) {
            this.taskId = taskId;
            this.exeTime = exeTime;
        }

        /** 计算还有多久到期，结果小于0则可以被queue取出 */
        @Override
        public long getDelay(TimeUnit unit) {
            return exeTime - System.currentTimeMillis();
        }

        /** 添加到queue的DelayTask，会根据这个方法来进行优先级排序 */
        @Override
        public int compareTo(Delayed o) {
            DelayTask other = (DelayTask) o;
            if (this.exeTime - other.exeTime <= 0) {
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