package com.junyi;


import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.junyi.entity.Book;
import com.junyi.entity.Person;
import com.junyi.entity.Shop;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * User: JY
 * Date: 2019/1/23 0023
 * Description:
 */

@Slf4j
public class aaa {
    static final int MAXIMUM_CAPACITY = 1 << 30;

    private static int cal(int n) {
        n -= 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    @Test
    public void func() {
        SimpleDateFormat format = new SimpleDateFormat("HH时mm分ss秒");
        format.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        log.info("{}", format.format(new Date()));
    }

    private void f1(Integer a) {
        a -= 1;
    }

}
