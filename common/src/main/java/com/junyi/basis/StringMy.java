package com.junyi.basis;

import java.util.StringJoiner;

/**
 * User: JY
 * Date: 2020/7/18 0018
 * Description:
 */
public class StringMy {
    public static void main(String[] args) {
        String[] names = {"Bob", "Alice", "Grace"};
        StringJoiner sj = new StringJoiner(", ", "Hello ", "!");    //指定了开头和结尾
        for (String name : names) {
            sj.add(name);
        }
        System.out.println(sj.toString());

        String s = String.join(", ", names);    //不能指定开头和结尾，更简洁
        System.out.println(s);
    }
}
