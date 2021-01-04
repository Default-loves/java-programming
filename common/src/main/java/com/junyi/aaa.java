package com.junyi;


import com.alibaba.fastjson.JSON;
import com.junyi.entity.Book;
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


    @Test
    public void func() {
        String s = "大车场实际计费时间：00时00分41秒";
        Pattern pattern = Pattern.compile("车辆类型改变为.*");
        Matcher matcher = pattern.matcher(s);
        String s2 = s.replaceAll("车辆类型改变为.*", "车辆类型改变为:临时车A");
        log.info(s);
        log.info(s2);
    }

    private void f1(Integer a) {
        a -= 1;
    }

}
