package com.junyi;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * TODO：有bug
 * 经典例子，fork/join统计单词数量
 * @time: 2021/5/7 17:28
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class ForkJoinMy2 {


    @Test
    public void test() {
        String[] strings = {"hello world", "hello me", "hello fork", "hello join", "fork join in world"};
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        CalWordTask task = new CalWordTask(strings, 0, strings.length);
        forkJoinPool.submit(task);
        Map<String, Integer> result;
        try {
            result = task.get();
            System.out.println(result.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    class CalWordTask extends RecursiveTask<Map<String, Integer>> {
        private String[] words;
        private int start;
        private int end;

        public CalWordTask(String[] words, int start, int end) {
            this.words = words;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Map<String, Integer> compute() {
            if (end - start == 1) {
                return cal(words[start]);
            }
            int mid = (end-start) / 2;
//            int mid = start + (end - start) >> 1;
            CalWordTask t1 = new CalWordTask(words, start, mid);
            t1.fork();
            CalWordTask t2 = new CalWordTask(words, mid, end);
            return merge(t2.compute(), t1.join());
        }

        private Map<String, Integer> merge(Map<String, Integer> a, Map<String, Integer> b) {
            HashMap<String, Integer> result = new HashMap<>();
            result.putAll(a);
            for (Map.Entry<String, Integer> entry : b.entrySet()) {
                result.put(entry.getKey(), result.getOrDefault(entry.getKey(), 0) + entry.getValue());
            }
            return result;
        }

        private Map<String, Integer> cal(String line) {
            String[] strings = line.split("\\s");
            HashMap<String, Integer> map = new HashMap<>();
            for (String string : strings) {
                map.put(string, map.getOrDefault(string, 0) + 1);
            }
            return map;
        }
    }
}
