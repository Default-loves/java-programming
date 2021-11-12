package com.junyi.basis;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @time: 2021/11/3 10:46
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class PriorityQueueMy {

    @Test
    public void test() {
        List<String> list = Arrays.asList("a", "c", "ab");
//        PriorityQueue<String> queue = new PriorityQueue<>((o1, o2) -> o2.compareTo(o1));      // 大顶堆
        PriorityQueue<String> queue = new PriorityQueue<>((o1, o2) -> o1.compareTo(o2));    // 小顶堆
        for (String item : list) {
            queue.offer(item);
        }

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
}
