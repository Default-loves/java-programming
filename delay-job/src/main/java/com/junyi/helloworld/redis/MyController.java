package com.junyi.helloworld.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @time: 2021/10/29 14:57
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@RestController
@Slf4j
public class MyController {
    @Autowired
    TaskProducer taskProducer;

    @GetMapping("/add")
    public Object add(@RequestParam("time") int maxDelaySecond) {
        int taskId = ThreadLocalRandom.current().nextInt(100);
        int delaySecond = ThreadLocalRandom.current().nextInt(maxDelaySecond);
        long exeTime = System.currentTimeMillis() + delaySecond * 1000L;

        log.info("【add—— taskId: {}, delaySecond: {}】", taskId, delaySecond);
        taskProducer.produce(taskId, exeTime);
        return "OK";
    }
}
