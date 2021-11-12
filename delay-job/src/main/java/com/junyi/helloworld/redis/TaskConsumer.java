package com.junyi.helloworld.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2021/10/29 11:51
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Service
public class TaskConsumer {

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    public void consumer() {
        new Thread(() -> {
            while (true) {
                Set<String> taskIdSet = stringRedisTemplate.opsForZSet().rangeByScore(Application.REDIS_KEY, 0, System.currentTimeMillis(), 0, 1);
                if (taskIdSet == null || taskIdSet.isEmpty()) {
                    System.out.println("没有任务");
                    try {
                        TimeUnit.MILLISECONDS.sleep(10 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    taskIdSet.forEach(id -> {
                        long result = stringRedisTemplate.opsForZSet().remove(Application.REDIS_KEY, id);
                        if (result == 1L) {
                            System.out.println("从延时队列中获取到任务，taskId:" + id + " , 当前时间：" + LocalDateTime.now());
                        }
                    });
                }
            }
        }).start();
    }
}
