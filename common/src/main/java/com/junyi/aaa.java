package com.junyi;


import com.alibaba.fastjson.JSON;
import com.junyi.entity.Book;
import com.junyi.entity.Shop;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
        Book book = new Book();
        Shop shop = new Shop(1, "big shop");
        book.setShop(shop);
        log.info(JSON.toJSONString(book));
        shop.setDescription("small shop");
        log.info(JSON.toJSONString(book));
    }

    private void f1(Integer a) {
        a -= 1;
    }

}
