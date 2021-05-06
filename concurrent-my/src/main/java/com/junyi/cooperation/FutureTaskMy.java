package com.junyi.cooperation;

import ch.qos.logback.classic.pattern.ThrowableHandlingConverter;
import com.junyi.ThreadPoolExecutorMy;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @time: 2021/4/30 14:42
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class FutureTaskMy {

    @Test
    public void futureTask() throws ExecutionException, InterruptedException {
        // 创建FutureTask
        FutureTask<Integer> futureTask
                = new FutureTask<>(() -> 1 + 2);
        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100)
                , r -> new Thread(r, "tea" + r.hashCode()));

        // 提交FutureTask
        executor.submit(futureTask);
        // 获取计算结果
        Integer result = futureTask.get();
        System.out.println(result);
    }


    @Test
    public void test() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100)
                , r -> new Thread(r, "tea" + r.hashCode()));

        FutureTask<String> ft2 = new FutureTask<>(new T2());
        FutureTask<String> ft1 = new FutureTask<>(new T1(ft2));

        executor.submit(ft1);
        executor.submit(ft2);

        try {
            String result = ft1.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    class T1 implements Callable<String> {
        private FutureTask<String> ft;

        public T1(FutureTask ft) {
            this.ft = ft;
        }

        @Override
        public String call() throws Exception {
            System.out.println("洗水壶");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("烧水");
            TimeUnit.SECONDS.sleep(15);

            String tea = ft.get();
            System.out.println("泡茶: " + tea);
            TimeUnit.SECONDS.sleep(1);
            return "上茶";
        }
    }


    class T2 implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("洗茶壶");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("洗茶杯");
            TimeUnit.SECONDS.sleep(2);

            System.out.println("拿茶叶");
            TimeUnit.SECONDS.sleep(1);
            return "Red Tea";
        }
    }
}
