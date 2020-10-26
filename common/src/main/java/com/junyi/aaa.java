package com.junyi;


import com.junyi.entity.Book;
import lombok.extern.slf4j.Slf4j;

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

        Stream.of(100, 200, 300, 400, 500)
                .mapToLong(e -> e * 10)
                .filter(e -> e > 2000)
                .forEach(System.out::println);

        String string = Optional.of("hi,")
                .map(e -> e + "Java")
                .map(e -> e + "技术")
                .map(e -> e + "栈").get();
        System.out.println(string);
    }
}
