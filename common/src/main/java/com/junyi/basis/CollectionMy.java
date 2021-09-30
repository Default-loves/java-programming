package com.junyi.basis;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @time: 2021/8/27 10:05
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class CollectionMy {


    @Test
    public void testRemove() {
        List<String> list = new ArrayList<>(Arrays.asList("abc", "bc", "c"));
        list.removeIf(t -> t.startsWith("a"));
        System.out.println(list.toString());
    }
}
