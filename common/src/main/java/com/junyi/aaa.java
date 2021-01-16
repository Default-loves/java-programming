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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.concurrent.*;
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
@SpringBootTest
public class aaa {

        @Resource
        private NoteService noteService;

        @Test
        public void saveNote() {
            Person entity = Person.builder().name("junyi").build();
            if (entity != null) {

            }
            if (entity == null) {

            }
            new ArrayList<>();

            try {
                noteService.save(entity);
            } catch (Exception e) {
                e.printStackTrace();
                // FIXME 我想在这里拿到的是 同步异常! [XXX]
                // FIXME 但是这里拿到的是 Transaction silently rolled back because it has been marked as rollback-only
                System.out.println(">>>>>>>>>> " + e.getMessage());
            }
        }

    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        generate(res, "", n, n);
        return res;
    }

    /**
     * @param res：最终的结果
     * @param s：处理的字符串
     * @param left：左括号剩余数量
     * @param right：右括号剩余数量
     */
    private void generate(List<String> res , String s, int left, int right) {
        if (left == 0 && right == 0) {
            res.add(s);
            return;
        }
        if (left != 0)
            generate(res, s + '(', left-1, right);     //当还有左括号的时候，可以添加左括号
        if (left < right)
            generate(res, s + ')', left, right-1);     //只有left小于right的时候，才可以添加右括号
    }

    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            int maxValue = 0;
            for (int j = i; j >= 0; j--) {
                if (nums[j] < nums[i] && dp[j] > maxValue)
                    maxValue = dp[j];
            }
            dp[i] = maxValue + 1;
        }
        return Arrays.stream(dp).max().getAsInt();
    }

}
