package com.junyi;


import com.junyi.entity.Book;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * User: JY
 * Date: 2019/1/23 0023
 * Description:
 */



@Slf4j
public class aaa {
    public static ConcurrentHashMap<String, Integer> CLOUD_PAYMENTtNX = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        Integer res = CLOUD_PAYMENTtNX.get(null);
        log.info(res + " ");


    }

}
