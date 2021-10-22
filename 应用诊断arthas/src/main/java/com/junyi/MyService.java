package com.junyi;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @time: 2021/10/16 15:41
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Service
public class MyService {

    public Book click() {
        return Book.builder()
                .id(1)
                .name("sum")
                .publishTime(LocalDateTime.of(1993, 7, 27, 10, 30))
                .build();
    }
}
