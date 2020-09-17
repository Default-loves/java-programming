package com.junyi;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * User: JY
 * Date: 2019/4/16 0016
 * Description:
 */
public class Solution {
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
        next = null;
        }
    }
    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode p = head.next;
        ListNode q = head;
        while (p != null && p.next != null) {
            if (p.val == q.val) {
                return true;
            }
            p = p.next.next;
            q = q.next;
        }
        return false;
    }



    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
//        System.out.println(s.firstMissingPositive(new int[] {3,4,-1,1}));
        byte[] b1 = "Hello".getBytes();
        byte[] b2 = "Hello".getBytes(StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(b1));
        System.out.println(Arrays.toString(b2));
    }
}
