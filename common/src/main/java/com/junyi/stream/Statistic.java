package com.junyi.stream;

import com.junyi.entity.Food;

import java.util.ArrayList;

/**
 * 统计相关
 * @time: 2021/2/4 10:39
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */

public class Statistic {

    public static void main(String[] args) {
        ArrayList<Food> list = new ArrayList<>();
        list.add(Food.builder().name("apple").money(3.1d).build());
        list.add(Food.builder().name("pear").money(10.01d).build());
        list.add(Food.builder().name("banana").money(0.01d).build());
        System.out.println(list.stream().mapToDouble(Food::getMoney).sum());;    //和
        System.out.println(list.stream().mapToDouble(Food::getMoney).max().getAsDouble());;    //最大
        System.out.println(list.stream().mapToDouble(Food::getMoney).min().getAsDouble());;    //最小
        System.out.println(list.stream().mapToDouble(Food::getMoney).average().getAsDouble());;    //平均值
    }
}
