package com.junyi.demo;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/**
 * User: JY
 * Date: 2020/5/9 0009
 * Description: 针对client5的读与读不能同时并发的问题，可以使用读写锁
 * 问题：当多个线程正在读取操作，那么修改操作需要等待所有正在读取的操作完成之后才可以修改
 */
public class Client6 {
    private int number = 0;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();
    private final Condition condition = writeLock.newCondition();
    private boolean writeComplete;

    private void read() {
        readLock.lock();
        while (!writeComplete) {
            System.out.println("read wait");
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("read number = "+ number);
        readLock.unlock();
    }
    private void write(int change) {
        writeLock.lock();
        number += change;
        System.out.println("write " + number);
        writeLock.unlock();
    }

    @Test
    public void test() throws InterruptedException {
        //开启一个线程修改数据
        new Thread(() -> {
            writeComplete = false;
            for (int i = 0; i < 100; i++) {
                write(1);
            }
//            System.out.println("增加 100 次已完成");
            writeComplete = true;
            writeLock.lock();
            condition.signal();
            writeLock.unlock();
        }).start();

        // 开启一个线程读取数据
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                read();
            }
        }).start();

        // 睡眠一秒保证线程执行完成
        Thread.sleep(1000);
    }
}