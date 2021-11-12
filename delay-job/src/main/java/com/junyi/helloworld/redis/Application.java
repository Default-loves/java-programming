package com.junyi.helloworld.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 使用 Redis 的 ZSet
 * 程序会一直运行一个消费者，定时去获取是否有到期数据
 * 通过   http://localhost:12351/add 来生产数据
 * @time: 2021/10/29 11:41
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@SpringBootApplication
public class Application implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public static final String REDIS_KEY = "delay_job";
    @Autowired
    TaskConsumer taskConsumer;

    @Override
    public void run(ApplicationArguments args) {
        taskConsumer.consumer();
    }
}
