package com.junyi;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * fork/join 分治任务
 *
 * fork() 会异步创建任务执行
 * joins() 会阻塞等待任务执行完毕
 *
 * 分治任务我们使用抽象类 ForkJoinTask 来构建任务
 * ForkJoinTask 有两个子类——RecursiveAction 和 RecursiveTask，RecursiveAction无返回值，RecursiveTask有返回值在
 * @time: 2021/5/7 17:13
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class ForkJoinMy {


    @Test
    public void test() {
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        Fibonacci task = new Fibonacci(10);
        Integer result;
        try {
            forkJoinPool.execute(task);
            result = task.get();
            System.out.println("result: " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    class Fibonacci extends RecursiveTask<Integer> {
        private int n;

        public Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            Fibonacci f1 = new Fibonacci(n - 1);
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            return f2.compute() + f1.join();
        }
    }
}
