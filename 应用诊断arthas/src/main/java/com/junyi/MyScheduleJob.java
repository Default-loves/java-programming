package com.junyi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @time: 2021/10/16 15:41
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Component
@Slf4j
public class MyScheduleJob {


    @Scheduled(fixedRate = 30 * 1000)
    public void job1() {
        log.info("schedule job1");
    }
}
