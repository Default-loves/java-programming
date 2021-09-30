package com.junyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @time: 2021/9/18 17:33
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ScheduledExecutorService scheduleExecutor() {
        return Executors.newScheduledThreadPool(3);
    }
}
