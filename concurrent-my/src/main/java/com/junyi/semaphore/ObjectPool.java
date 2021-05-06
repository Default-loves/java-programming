package com.junyi.semaphore;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * 对象池，初始化10个对象，可以允许最多10个线程获取对象使用，使用信号量实现限流
 * @time: 2021/4/27 11:34
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class ObjectPool<T, R> {

    final List<T> pool;
    // 用信号量实现限流器
    final Semaphore sem;
    // 构造函数
    ObjectPool(int size, Class<T> cls) throws IllegalAccessException, InstantiationException {
        T t = cls.newInstance();
        pool = new Vector<T>(){};
        for(int i = 0; i < size; i++){
            pool.add(t);
        }
        sem = new Semaphore(size);
    }

    // 利用对象池的对象，调用func
    R exec(Function<T, R> func) {
        T t = null;
        try {
            sem.acquire();
            t = pool.remove(0);
            return func.apply(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pool.add(t);
            sem.release();
        }
        return null;
    }

    @Test
    public void test() throws InstantiationException, IllegalAccessException {
        // 创建对象池
        ObjectPool<Long, String> pool = new ObjectPool<Long, String>(10, Long.class);
        // 通过对象池获取t，之后执行
        pool.exec(t -> {
            System.out.println(t);
            return t.toString();
        });
    }
}