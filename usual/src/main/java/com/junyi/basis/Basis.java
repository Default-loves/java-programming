package com.junyi.basis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * User: JY
 * Date: 2018/12/30 0030
 * Description:
 */
public class Basis {
    public static HashMap<String, Integer> map = new HashMap<String, Integer>(){
        {
            put("a1", 12);
            put("a2", 100);
        }
    };
    public static void main(String[] args) {
        System.out.println(map.get("a1"));
        ArrayList<Integer> data = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8));
        System.out.println(data);

    }
}
