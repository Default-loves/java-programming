package com.junyi.basis;

import com.alibaba.fastjson.JSON;
import com.junyi.entity.Book;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        if (n < 2) {
            return cost[0];
        }
        int[] dp = new int[n];
        dp[0] = cost[0];
        dp[1] = cost[1];
        for (int i = 2; i < n; i++) {
            dp[i] = min(dp[i-1], dp[i-2]) + cost[i];
        }
        return min(dp[n-1], dp[n-2]);
    }
}
