package com.junyi.algorithm;

import javafx.util.Pair;

import java.io.*;
import java.util.*;

/**
 * @time: 2020/8/18 19:34
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class LeetCodeSolution {
    static class AllOne {
        // K 为字符串标识， V 为 累计值
        private final Map<String, Integer> map1;
        // K 为累计值， V 为 对应的链表节点
        private final Map<Integer, ListNode> map2;

        private final ListNode head;  // 头结点，双向链表的头结点的累计值为最大，尾节点最小
        private final ListNode tail;  // 尾节点

        /** Initialize your data structure here. */
        public AllOne() {
            map1 = new HashMap<>();
            map2 = new HashMap<>();
            head = new ListNode(0);
            tail = new ListNode(0);
            head.next = tail;
            tail.pre = head;
        }

        /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
        public void inc(String key) {
            if (map1.containsKey(key)) {
                Integer val = map1.get(key);
                map1.put(key, val+1);
                ListNode node = map2.get(val);
                node.keys.remove(key);
                // 当前所在节点的累加值与前一个节点的累加值不连续，需要创建新的节点，保存累加值为val+1的key
                if (node.pre == head || node.pre.val > val + 1) {
                    ListNode nNode = new ListNode(val + 1);
                    nNode.keys.add(key);
                    nNode.next = node;
                    nNode.pre = node.pre;
                    node.pre.next = nNode;
                    node.pre = nNode;
                    map2.put(val+1, nNode);
                } else {
                    node.pre.keys.add(key);
                }
                // 删除空节点
                if (node.keys.size() == 0) {
                    node.pre.next = node.next;
                    node.next.pre = node.pre;
                    map2.remove(val);
                }
            } else {
                map1.put(key, 1);
                if (map2.containsKey(1)) {
                    map2.get(1).keys.add(key);
                } else {
                    ListNode nNode = new ListNode(1);
                    nNode.keys.add(key);
                    nNode.next = tail;
                    nNode.pre = tail.pre;
                    tail.pre.next = nNode;
                    tail.pre = nNode;
                    map2.put(1, nNode);
                }
            }
        }

        /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
        public void dec(String key) {
            if (map1.containsKey(key)) {
                Integer val = map1.get(key);
                ListNode node = map2.get(val);
                node.keys.remove(key);
                if (val == 1) {
                    map1.remove(key);
                } else {
                    map1.put(key, val-1);
                    if (node.next == tail || node.next.val < val-1) {
                        ListNode nNode = new ListNode(val - 1);
                        nNode.keys.add(key);
                        nNode.next = node.next;
                        nNode.pre = node;
                        node.next = nNode;
                        nNode.next.pre = nNode;
                        map2.put(val-1, nNode);
                    } else {
                        node.next.keys.add(key);
                    }
                }
                if (node.keys.size() == 0) {
                    map2.remove(val);
                    node.pre.next = node.next;
                    node.next.pre = node.pre;
                }
            }
        }

        /** Returns one of the keys with maximal value. */
        public String getMaxKey() {
            if (head.next == tail) {
                return "";
            }
            return head.next.keys.iterator().next();
        }

        /** Returns one of the keys with Minimal value. */
        public String getMinKey() {
            if (head.next == tail) {
                return "";
            }
            return tail.pre.keys.iterator().next();
        }

        class ListNode {
            int val;
            ListNode pre, next;
            Set<String> keys;
            ListNode(int x) { val = x; keys = new HashSet<>();}
        }
    }


    /**
     * Your AllOne object will be instantiated and called as such:
     * AllOne obj = new AllOne();
     * obj.inc(key);
     * obj.dec(key);
     * String param_3 = obj.getMaxKey();
     * String param_4 = obj.getMinKey();
     */

    public static void main(String[] args) {
        LeetCodeSolution s = new LeetCodeSolution();
        AllOne allOne = new AllOne();
        allOne.inc("a");
        allOne.inc("b");
        allOne.inc("b");
        allOne.inc("c");
        allOne.dec("b");
        allOne.dec("b");
        allOne.dec("a");
        System.out.println(allOne.getMinKey());
    }
}
