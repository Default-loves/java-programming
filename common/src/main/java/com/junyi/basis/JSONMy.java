package com.junyi.basis;

import com.alibaba.fastjson.JSON;
import com.junyi.entity.Book;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @time: 2020/9/22 16:19
 * @version: 1.0
 * @author: junyi Xu
 * @description: use alibaba fast-json
 */
@Slf4j
public class JSONMy {

    public static void main(String[] args) {
        Book book1 = new Book(1, "apple");
        Book book2 = new Book(2, "pear");
        ArrayList<Book> list = new ArrayList<>();
        list.add(book1);
        list.add(book2);
        String s = JSON.toJSONString(list);
        log.info(s);
        List<Book> parseList = JSON.parseArray(s, Book.class);
    }
}
