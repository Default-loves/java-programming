package com.junyi;


import com.junyi.entity.Book;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * User: JY
 * Date: 2019/1/23 0023
 * Description:
 */



@Slf4j
public class aaa {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
        log.info(LocalDateTime.now().format(formatter));
        log.info(LocalDateTime.now().toString());
    }
}
