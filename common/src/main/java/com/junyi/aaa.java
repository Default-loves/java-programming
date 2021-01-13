package com.junyi;


import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.junyi.entity.Book;
import com.junyi.entity.Person;
import com.junyi.entity.Shop;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
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
        String[] list = new String[] {"ca","bb","ac"};
        int res = minDeletionSize(list);
        log.info(res + "");
    }

    public int minDeletionSize(String[] A) {
        int n = A.length;
        int m = A[0].length();
        // point[i] = true，说明A[j]字符串不再需要和A[j+1]字符串比较了
        boolean[] point = new boolean[n-1];
        int ans = 0;

        search:
        for (int j = 0; j < m; ++j) {
            for (int i = 0; i < n-1; ++i) {
                if (!point[i] && A[i].charAt(j) > A[i+1].charAt(j)) {   // 降序序列，那么需要删除该列，统计结果加1
                    ans++;
                    continue search;
                }
            }
            // 更新 point 数组
            // 需要注意的是，true值的个数应该是逐渐增加的，即不能够判断后赋值为 false
            for (int i = 0; i < n-1; ++i) {
                if (A[i].charAt(j) < A[i+1].charAt(j))
                    point[i] = true;
            }
        }
        return ans;
    }

}
