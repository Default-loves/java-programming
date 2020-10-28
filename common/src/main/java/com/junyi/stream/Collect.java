package com.junyi.stream;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User: JY
 * Date: 2020/7/22 0022
 * Description:
 */
public class Collect {
    public static void main(String[] args) {
//        //输出为List
//        Stream<String> stream = Stream.of("Apple", "", null, "Pear", "  ", "Orange");
//        List<String> list1 = stream.filter(s -> s != null && !s.isBlank()).collect(Collectors.toList());
//        System.out.println(list1);
//
//        //输出为Array
//        List<String> list2 = List.of("Apple", "Banana", "Orange");
//        String[] array = list2.stream().toArray(String[]::new);
//
//        //输出为Map
//        stream = Stream.of("APPL:Apple", "MSFT:Microsoft");
//        Map<String, String> map = stream
//                .collect(Collectors.toMap(
//                        // 把元素s映射为key:
//                        s -> s.substring(0, s.indexOf(':')),
//                        // 把元素s映射为value:
//                        s -> s.substring(s.indexOf(':') + 1)));
//        System.out.println(map);
//
//        //分组输出
//        List<String> list = List.of("Apple", "Banana", "Blackberry", "Coconut", "Avocado", "Cherry", "Apricots");
//        Map<String, List<String>> groups = list.stream()
//                .collect(Collectors.groupingBy(s -> s.substring(0, 1), Collectors.toList()));
//        System.out.println(groups);
    }
}
