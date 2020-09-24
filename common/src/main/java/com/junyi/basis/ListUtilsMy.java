package com.junyi.basis;

import org.apache.commons.collections4.ListUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @time: 2020/9/19 10:45
 * @version: 1.0
 * @author: junyi Xu
 * @description: ListUtils.partition，对 List 进行分组
 */
public class ListUtilsMy {

    public static void main(String[] args) {

        List<Integer> source = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        List<List<Integer>> target = ListUtils.partition(source, 3);
        System.out.println(target);
    }
}
