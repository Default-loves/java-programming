package com.junyi.helloworld.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @time: 2021/10/29 11:42
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Service
public class TaskProducer {

    @Autowired
    StringRedisTemplate stringRedisTemplate;



    public void produce(Integer taskId, long exeTime) {
        stringRedisTemplate.opsForZSet().add(Application.REDIS_KEY, String.valueOf(taskId), exeTime);
    }

}
