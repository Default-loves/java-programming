package com.junyi.stream;

import com.junyi.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @time: 2020/8/14 20:38
 * @version: 1.0
 * @author: junyi Xu
 * @description: List 转变为 Map
 */
@Slf4j
public class List2Map {
    public static void main(String[] args) {
        // 1. 只配置了Key和Value
        Stream<String> data1 = Stream.of("apple", "banana", "orange");
        Map<Character, String> map1 = data1.collect(Collectors.toMap(
                s1 -> s1.charAt(0), String::toUpperCase));
        System.out.println(map1);

        // 2. 还配置了Key冲突处理
        Stream<String> data2 = Stream.of("apple", "banana", "apricot", "orange");
        Map<Character, String> map2 = data2.collect(Collectors.toMap(
                s1 -> s1.charAt(0), String::toUpperCase, (s1, s2) -> s1 + "|" + s2));
        System.out.println(map2);

        // 3. 第四个参数作用未知
        Stream<String> data3 = Stream.of("apple", "banana", "apricot", "orange");
        Map<Character, String> map3 = data3.collect(Collectors.toMap(
                s1 -> s1.charAt(0), String::toUpperCase, (s1, s2) -> s1 + "|" + s2, HashMap::new));
        System.out.println(map3);
    }

    /**
     * 如果 toMap 中获取的值是 null，那么会导致 NullPointException
     */
    @Test
    public void nullPointException() {
        List<Book> list = buildList();
        try {
            Map<Integer, String> map = list.stream().collect(Collectors.toMap(Book::getId, Book::getName));
        } catch (Exception e) {
            log.info(e.toString());
        }
    }

    @Test
    public void fixNullPointException() {
        List<Book> list = buildList();
        Map<Integer, String> map = list.stream().collect(HashMap::new, (m, v) -> m.put(v.getId(), v.getName()), HashMap::putAll);
        log.info(map.toString());

    }

    private List<Book> buildList() {
        Book b1 = new Book();
        b1.setId(1);
        b1.setName("abc");
        Book b2 = new Book();
        b2.setId(2);
        b2.setName(null);   // 坑点
        return Arrays.asList(b1, b2);
    }
}
