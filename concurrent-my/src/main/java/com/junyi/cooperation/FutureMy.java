package com.junyi.cooperation;

import org.junit.jupiter.api.Test;

import javax.xml.transform.Result;
import java.util.concurrent.*;

/**
 * 通过线程池的 submit 方法执行任务
 * @time: 2021/4/30 15:34
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class FutureMy {

    //
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100)
                , r -> new Thread(r, "tea" + r.hashCode()));
        Future<Integer> future = executor.submit(() -> 1 + 1);
        Integer result = future.get();
        System.out.println(result);
    }

    @Test
    public void test3() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100)
                , r -> new Thread(r, "tea" + r.hashCode()));
        executor.submit(() -> {
            System.out.println("running");
        });
    }

    @Test
    public void test() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100)
                , r -> new Thread(r, "tea" + r.hashCode()));


        Data data = new Data();
        Future<Data> future = executor.submit(new RunnableMy(data), data);
        Data response = future.get();
        System.out.println(response.getId() + response.getName());

    }

    static class RunnableMy implements Runnable {
        private Data data;

        public RunnableMy(Data data) {
            this.data = data;
        }

        @Override
        public void run() {
            data.setId(1);
            data.setName("apple");
        }
    }


    static class Data {
        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
