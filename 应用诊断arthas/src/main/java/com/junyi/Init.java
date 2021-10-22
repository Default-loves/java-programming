package com.junyi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @time: 2021/10/16 15:42
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
@Component
public class Init implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("start job");
        while (true) {
            Thread.sleep(10 * 1000);
            LocalDateTime now = LocalDateTime.now();
            log.info("now: {}", now);
        }
    }
}
