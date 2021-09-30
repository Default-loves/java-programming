package com.junyi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @time: 2021/9/18 17:35
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Component
public class ScheduleJob {



    // 每秒执行一次
    @Scheduled(cron = "0/1 * * * * ? ")
    public void test() {
        // 时分秒
        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        // 日志打印
        System.out.println(nowTime + "   任务【1】执行  线程:" + Thread.currentThread().getName());
    }

    // 每5秒执行一次
    @Scheduled(cron = "0/5 * * * * ? ")
    public void test2() {
        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println(nowTime + "   任务【2】执行  线程:" + Thread.currentThread().getName());
        try {
            // 模拟耗时任务，阻塞2s
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
