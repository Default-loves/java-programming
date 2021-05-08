package com.junyi;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @time: 2021/5/8 14:52
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class ThreadLocalMy {

    @Test
    public void test() {
        ExecutorService es = Executors.newFixedThreadPool(3);
        ThreadLocal tl = new ThreadLocal<>();
        es.execute(()->{
            //ThreadLocal增加变量
            Object obj = new Object();
            tl.set(obj);
            try {
                // 省略业务逻辑代码
            }finally {
                //手动清理ThreadLocal
                tl.remove();
            }
        });
    }
}
