package com.junyi;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

/**
 * 函数式接口的使用
 * Consumer<>   能够将方法内的操作由调用者来定义
 * @time: 2021/11/1 15:00
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class FunctionalInterfaceMy {

    @Test
    public void test() {
        doing("apple", s -> {
            System.out.println("in side: " + s);
        });

        doing("water", s -> {
            System.out.println("in side: " + s);
        });
    }


    public void doing(String a, Consumer<String> fun) {
        System.out.println("+++++++ " + a);
        String b = a + "_test";
        fun.accept(b);
    }
}
