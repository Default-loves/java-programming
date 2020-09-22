package com.junyi;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: JY
 * Date: 2018/12/28 0028
 * Description: 对于 get 操作会将元素移动到末尾
 */
public class LinkedHashMapMy {
    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new LinkedHashMap<>(10, 0.75f, true);
        map.put(1,2);
        map.put(3,4);
        map.put(5,6);
        map.put(7,8);
        for (Map.Entry e : map.entrySet()){
            System.out.println(e.getKey());     // 1 3 5 7
        }
        map.put(3,9);
        System.out.println("---");
        for (Map.Entry e : map.entrySet()){
            System.out.println(e.getKey());     // 1 5 7 3
        }
        map.get(5);
        System.out.println("---");
        for (Map.Entry e : map.entrySet()){
            System.out.println(e.getKey());     // 1 7 3 5
        }
    }
}
