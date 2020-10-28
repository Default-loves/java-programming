package com.junyi.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * User: JY
 * Date: 2020/7/22 0022
 * Description: 创建Stream的方法
 */
public class CreateStream {
    public static void main(String[] args) throws IOException {
//        //1
//        Stream<String> stream = Stream.of("A", "B", "C", "D");
//        // forEach()方法相当于内部循环调用，
//        // 可传入符合Consumer接口的void accept(T t)的方法引用：
//        stream.forEach(System.out::println);
//        //2
//        Stream<String> stream1 = Arrays.stream(new String[] { "A", "B", "C" });
//        Stream<String> stream2 = List.of("X", "Y", "Z").stream();
//        stream1.forEach(System.out::println);
//        stream2.forEach(System.out::println);
//
//        /** 3. custom generate method */
//        Stream<Integer> natual = Stream.generate(new NatualSupplier());
//        // 注意：无限序列必须先变成有限序列再打印:
//        natual.limit(20).forEach(System.out::println);
//
//        /** 4. Files.lines */
//        try (Stream<String> lines = Files.lines(Paths.get("/path/to/file.txt"))) {
//            //...
//        }
//
//        /** 5. from Regex Pattern */
//        Pattern p = Pattern.compile("\\s+");
//        Stream<String> s = p.splitAsStream("The quick brown fox jumps over the lazy dog");
//        s.forEach(System.out::println);
//
//
//    }
//    static class NatualSupplier implements Supplier<Integer> {
//        int n = 0;
//        public Integer get() {
//            n++;
//            return n;
//        }
    }
}
