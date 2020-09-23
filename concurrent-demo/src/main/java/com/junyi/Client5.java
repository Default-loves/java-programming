package com.junyi;

import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: JY
 * Date: 2020/5/9 0009
 * Description: 之前都是使用synchronize，现在使用ReentrantRock
 * ReentrantRock还支持设置获取锁的等待时间，以及设置唤醒的时间
 * 问题：这儿的读取操作需要获取锁，其实不用获取锁，当没有写入的时候，允许多个线程同时读取，提高并发效率
 *
 */
public class Client5 {
    private int number = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean writeComplete;

    private void read() {
        lock.lock();
        while (!writeComplete) {
            try {
                System.out.println("read wait");
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("read number = "+ number);
        lock.unlock();
    }

    private void write(int change) throws InterruptedException {
        if(lock.tryLock(1, TimeUnit.SECONDS)) {
            number += change;
            System.out.println("write: " + number);
            lock.unlock();
        } else {
            System.out.println("Can't catch lock in 1 second");
        }
    }

    @Test
    public void test() throws InterruptedException {
        //开启一个线程修改数据
        new Thread(() -> {
            writeComplete = false;
            for (int i = 0; i < 100; i++) {
                try {
                    write(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("增加 100 次已完成");
            writeComplete = true;
            lock.lock();
            condition.signalAll();
            lock.unlock();
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