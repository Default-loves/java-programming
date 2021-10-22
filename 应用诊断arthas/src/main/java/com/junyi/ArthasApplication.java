package com.junyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @time: 2021/10/16 15:40
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@SpringBootApplication
@EnableScheduling
public class ArthasApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArthasApplication.class, args);
    }
}
