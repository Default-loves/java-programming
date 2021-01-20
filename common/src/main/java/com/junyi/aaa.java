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


    public int[][] findContinuousSequence(int target) {
        int capacity = 30;
        int[][] res = new int[capacity][];
        int left = 1, right = 2;
        int k = 0;
        while (right <= target/2+1) {
            int sum = (left + right)*(right - left + 1)/2;
            if (sum > target)
                left++;
            else if (sum < target)
                right++;
            else {
                int[] tmp = new int[right-left+1];
                for (int i = 0; i < tmp.length; i++) {
                    tmp[i] = left+i;
                }
                res[k++] = tmp;
                left++;
                if (k == capacity) {
                    capacity += capacity;
                    res = Arrays.copyOf(res, capacity);
                }
            }
        }
        res = Arrays.copyOf(res, k);
        return res;
    }

    public static void main(String[] args) {

    }

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

    public int maxDistance(int[][] grid) {
        int[] dx = {0, 0, 1, -1};	// 4个方向
        int[] dy = {-1, 1, 0, 0};
        Queue<int[]> queue = new ArrayDeque<>();
        int n = grid.length;
        for (int i = 0; i < n; i++) {	//查找所有陆地的位置
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    queue.add(new int[]{i, j});
                }
            }
        }
        if (queue.size() == n * n || queue.size() == 0) {    // 全为陆地或者全为海洋
            return -1;
        }
        int[] point = null;
        while (!queue.isEmpty()) {
            point = queue.poll();
            for (int i = 0; i < 4; i++) {
                int x = point[0] + dx[i];
                int y = point[1] + dy[i];
                if (x < 0 || x > n - 1 || y < 0 || y > n - 1 || grid[x][y] != 0) {
                    continue;
                }
                grid[x][y] = grid[point[0]][point[1]] + 1;	// 修改原数据内容存放距离信息
                queue.add(new int[]{x, y});
            }
        }
        return grid[point[0]][point[1]]-1;
    }

}
