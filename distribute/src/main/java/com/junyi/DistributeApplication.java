package com.junyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @time: 2021/4/15 9:25
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@SpringBootApplication
public class DistributeApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DistributeApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
