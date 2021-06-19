package com.junyi.stream;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: JY
 * Date: 2020/7/22 0022
 * Description: 通过reduce操作聚合为HashMap
 */
public class Reduce {
    public static void main(String[] args) {
        // 按行读取配置文件:
//        List<String> props = Arrays.asList("profile=native", "debug=true", "logging=warn", "interval=500");
//        Map<String, String> map = props.stream()
//                // 把k=v转换为Map[k]=v:
//                .map(kv -> {
//                    String[] ss = kv.split("\\=", 2);
//                    return Map.of(ss[0], ss[1]);
//                })
//                // 把所有Map聚合到一个Map:
//                .reduce(new HashMap<>(), (m, kv) -> {
//                    m.putAll(kv);
//                    return m;
//                });
//
//        System.out.println(JSON.toJSONString(map));
    }
}
