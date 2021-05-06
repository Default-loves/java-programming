package com.junyi.semaphore;

import java.util.concurrent.Semaphore;

/**
 *
 * 信号量保证互斥
 *
 * 比如有两个线程t1和t2同时访问方法addOne，t1执行acquire后值变为0，t2执行acquire后值变为-1，因为acquire操作是原子性且值具备可见性
 * t1 能够往下执行，而t2由于值为-1不能往下执行所以阻塞了
 * 当t1执行完毕后执行release操作，信号量自增1变为0，线程t2被唤醒执行
 *
 * @time: 2021/4/27 11:17
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class SemaphoreMy {


    static int count;
    //初始化信号量
    static final Semaphore s = new Semaphore(1);
    //用信号量保证互斥
    static void addOne() {
        try {
            s.acquire();
            count+=1;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            s.release();
        }
    }
}
