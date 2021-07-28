package com.junyi.mqtt;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @time: 2020/11/18 9:08
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class aaa {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {

        int k = 10;
        for (int i = 0; i < k; i++) {
            log.info("{}", (int) (i / (float) k * 100));

        }


    }
}
