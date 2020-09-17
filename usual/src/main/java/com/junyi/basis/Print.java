package com.junyi.basis;

import java.util.Arrays;

/**
 * User: JY
 * Date: 2020/7/17 0017
 * Description:
 */
public class Print {
    public static void main(String[] args) {

    }

    /**
     * 打印多维数组
     */
    private void printMultidimensionalArrays() {
        int[][] array = {
                {1,2},
                {3,4,5}
        };
        System.out.println(Arrays.deepToString(array));
    }
}
