package com.junyi.basis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @time: 2021/1/19 10:23
 * @version: 1.0
 * @author: junyi Xu
 * @description: 正则表达式，常见的几个使用方法
 */
@Slf4j
public class PatternMy {

    @Test
    public void test() {
        String re = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";    // 匹配邮箱的正则表达式
        String source = "123456789@qq.com";
        log.info("is match? {}", source.matches(re));
    }

    @Test
    public void test2() {
        Pattern pattern = Pattern.compile("(\\d{3,4})\\-(\\d{7,8})");
        pattern.matcher("010-12345678").matches(); // true
        pattern.matcher("021-123456").matches(); // true
        pattern.matcher("022#1234567").matches(); // false
        // 获得Matcher对象:
        Matcher matcher = pattern.matcher("010-12345678");
        if (matcher.matches()) {
            String whole = matcher.group(0); // "010-12345678", 0表示匹配的整个字符串
            String area = matcher.group(1); // "010", 1表示匹配的第1个子串
            String tel = matcher.group(2); // "12345678", 2表示匹配的第2个子串
            System.out.println(whole);
            System.out.println(area);
            System.out.println(tel);
        }
    }

    /** 匹配时间，获取时分秒 */
    @Test
    public void test3() {
        Pattern pattern = Pattern.compile("(\\d*):(\\d*):(\\d*)");
        String source = "10:59:27";
        Matcher matcher = pattern.matcher(source);
        if (matcher.matches()) {
            log.info(matcher.group(0));
            log.info(matcher.group(1));
            log.info(matcher.group(2));
            log.info(matcher.group(3));
        }
    }

    /** 非贪婪匹配，在后面添加“？” */
    @Test
    public void test4() {
        Pattern pattern = Pattern.compile("(\\d+?)(0*)");
        Matcher matcher = pattern.matcher("1230000");
        if (matcher.matches()) {
            System.out.println("group1=" + matcher.group(1)); // "123"
            System.out.println("group2=" + matcher.group(2)); // "0000"
        }
    }

    /** 非贪婪匹配，注意第一个“?”表达匹配0个或者1个，第二个“?”才是表达非贪婪匹配 */
    @Test
    public void test4a() {
        Pattern pattern = Pattern.compile("(\\d??)(0*)");
        Matcher matcher = pattern.matcher("0000");
        if (matcher.matches()) {
            System.out.println("group1=" + matcher.group(1)); // ""
            System.out.println("group2=" + matcher.group(2)); // "0000"
        }
    }

    /** 搜索字符串 */
    @Test
    public void test5() {
        String s = "the quick brown fox jumps over the lazy dog.";
        Pattern p = Pattern.compile("\\wo\\w");
        Matcher m = p.matcher(s);
        while (m.find()) {
            String sub = s.substring(m.start(), m.end());
            System.out.println(sub);
        }
    }

    /** 替换字符串 */
    @Test
    public void test6() {
        String s = "The     quick\t\t brown   fox  jumps   over the  lazy dog.";
        String r = s.replaceAll("\\s+", " ");
        System.out.println(r); // "The quick brown fox jumps over the lazy dog."
    }

    /** 替换字符串，对原有字符的增强，使用 $1 来引用匹配到的字符串 */
    @Test
    public void test7() {
        String s = "the quick brown fox jumps over the lazy dog.";
        String r = s.replaceAll("\\s([a-z]{4})\\s", " <b>$1</b> ");
        System.out.println(r);      // the quick brown fox jumps <b>over</b> the <b>lazy</b> dog.
    }
}
