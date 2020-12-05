package com.junyi;


import com.junyi.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * User: JY
 * Date: 2019/1/23 0023
 * Description:
 */

@Slf4j
public class aaa {

    public static void main(String[] args) {

        Integer a = null;
        try {
            Thread.sleep(1000);
            a = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Assert.notNull(a, "a is null");
        } catch (Exception e) {
            e.printStackTrace();
        }

        IntStream.rangeClosed(1, 10).forEach(System.out::println);
        log.info("End");

    }

}
