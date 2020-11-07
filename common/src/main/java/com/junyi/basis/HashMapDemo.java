package com.junyi.basis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @time: 2020/11/7 14:59
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class HashMapDemo {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("apple", 1);
        map.put("apple", 3);
        /**
         * 如果key存在那么就获取对应的value,如果key不存在,则使用mappingFunction即第二个参数的返回值
         */
        Integer countB = map.computeIfAbsent("banana", k -> 3);
        log.info(String.valueOf(countB));
        log.info(JSON.toJSONString(map));

    }
}
