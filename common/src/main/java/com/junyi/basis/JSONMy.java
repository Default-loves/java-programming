package com.junyi.basis;

import com.alibaba.fastjson.JSON;
import com.junyi.entity.Book;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static java.lang.Math.abs;
import static java.lang.Math.min;

/**
 * @time: 2020/9/22 16:19
 * @version: 1.0
 * @author: junyi Xu
 * @description: use alibaba fast-json
 */
@Slf4j
public class JSONMy {

    public static void main(String[] args) {
        Book book1 = Book.builder().id(1).name("apple").build();
        Book book2 = Book.builder().id(2).name("pear").build();
        ArrayList<Book> list = new ArrayList<>();
        list.add(book1);
        list.add(book2);
        String s = JSON.toJSONString(list);
        log.info(s);
        List<Book> parseList = JSON.parseArray(s, Book.class);
    }

    public int longestValidParentheses(String s) {
        int result = 0;
        int startIndex = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
                continue;
            }
            if (stack.isEmpty()) {
                startIndex = i + 1;
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    result = Math.max(result, i - startIndex + 1);
                } else {
                    result = Math.max(result, i - stack.peek());
                }
            }
        }
        return result;
    }
}
