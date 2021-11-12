package com.junyi.basis;

import com.junyi.entity.Book;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal 的值只能够在当前线程获取，在子线程是不能获取的
 * InheritableThreadLocal 的值可以传递到子线程，从而子线程能够获取到父线程的 值
 * @time: 2021/11/3 17:14
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class ThreadLocalMy2 {

//    private static ThreadLocal<Book> threadLocal = new ThreadLocal<>();
    private static InheritableThreadLocal<Book> threadLocal = new InheritableThreadLocal<>();


    public static void main(String[] args) throws ParseException, IOException, InterruptedException {
        threadLocal.set(Book.builder().id(1).name("my fun").build());

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(MyJob());

        Thread.sleep(1 * 1000);
        System.out.println(threadLocal.get());
        executorService.shutdown();
    }

    private static Runnable MyJob() {
        return () -> {
            try {
                Book book = threadLocal.get();
                System.out.println("sub thread: " + book.toString());
                book.setName("change fun");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
