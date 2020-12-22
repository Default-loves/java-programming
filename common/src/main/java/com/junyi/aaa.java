package com.junyi;


import com.alibaba.fastjson.JSON;
import com.junyi.entity.Book;
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
        Book b1 = new Book();
        b1.setId(1);
        b1.setName("abc");
        Book b2 = new Book();
        b2.setId(2);
        b2.setName("eft");
        List<Book> list = new ArrayList();
        list.add(b1);
        list.add(b2);
        Map<Integer, String> map = list.stream().collect(Collectors.toMap(Book::getId, Book::getName));
    }

    private void f1(Integer a) {
        a -= 1;
    }

}
