package com.junyi.helloworld;

import org.springframework.data.redis.repository.query.RedisOperationChain;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2020/12/19 13:00
 * @version: 1.0
 * @author: junyi Xu
 * @description: 使用 Redis 的 ZSet
 */
public class RedisZSetMy {

//    public void test() {
//
//        TaskProducer taskProducer = new TaskProducer();
//        //创建 3个任务，并设置超时间为 10s 5s 7s
//        taskProducer.produce(1, System.currentTimeMillis() + 10000);
//        taskProducer.produce(2, System.currentTimeMillis() + 5000);
//        taskProducer.produce(3, System.currentTimeMillis() + 7000);
//
//        System.out.println("等待任务执行===========");
//
//        //消费端从redis中消费任务
//        TaskConsumer taskConsumer = new TaskConsumer();
//        taskConsumer.consumer();
//    }
//
//
//    class TaskProducer {
//
//        public void produce(Integer taskId, long exeTime) {
//            System.out.println("加入任务， taskId: " + taskId + ", exeTime: " + exeTime + ", 当前时间：" + LocalDateTime.now());
//            RedisOps.getJedis().zadd(RedisOps.key, exeTime, String.valueOf(taskId));
//        }
//    }
//
//    class TaskConsumer {
//
//        public void consumer() {
//
//            Executors.newSingleThreadExecutor().submit(new Runnable() {
//                @Override
//                public void run() {
//                    while (true) {
//                        Set<String> taskIdSet = RedisOps.getJedis().zrangeByScore(RedisOps.key, 0, System.currentTimeMillis(), 0, 1);
//                        if (taskIdSet == null || taskIdSet.isEmpty()) {
//                            //System.out.println("没有任务");
//                        } else {
//                            taskIdSet.forEach(id -> {
//                                long result = RedisOps.getJedis().zrem(RedisOps.key, id);
//                                if (result == 1L) {
//                                    System.out.println("从延时队列中获取到任务，taskId:" + id + " , 当前时间：" + LocalDateTime.now());
//                                }
//                            });
//                        }
//                        try {
//                            TimeUnit.MILLISECONDS.sleep(300);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
//        }
//    }
}
