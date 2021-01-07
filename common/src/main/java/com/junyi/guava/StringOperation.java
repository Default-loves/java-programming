package com.junyi.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * @time: 2021/1/7 15:14
 * @version: 1.0
 * @author: junyi Xu
 * @description: Guava 中对字符串的操作
 */
@Slf4j
public class StringOperation {

    /** 字符拼接 */
    @Test
    public void join() {
        ArrayList<String> list = Lists.newArrayList("a", "b", "c", null);
        String join = Joiner.on(",").skipNulls().join(list);    // 跳过 null 值
        System.out.println(join); // a,b,c

        String join1 = Joiner.on(",").useForNull("default").join("旺财", "汤姆", "杰瑞", null);   // 赋值 null 值
        System.out.println(join1); // 旺财,汤姆,杰瑞,default
    }

    /** 字符切割 */
    @Test
    public void split() {
        String str = ",a ,,b ,";
        Iterable<String> split = Splitter.on(",")
                .omitEmptyStrings() // 忽略空值
                .trimResults() // 过滤结果中的空白
                .split(str);
        split.forEach(System.out::println);
    }
}
