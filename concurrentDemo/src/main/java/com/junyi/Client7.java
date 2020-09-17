package com.junyi;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.*;

/**
 * User: JY
 * Date: 2020/5/9 0009
 * Description: 针对client6的问题，可以使用乐观锁，乐观锁的特点是读的过程允许写，所以需要判断读取的数据是否有修改过，我们之前的都是悲观锁，悲观锁的特点是读的过程不允许写，所以我们都是等待所有的写入操作完成之后才读
 * 乐观锁适用于读多写少的操作
 */
public class Client7 {
    private int number = 0;
    private final StampedLock lock = new StampedLock();
    private boolean writeComplete;

    private void read() {
        long stamp = lock.tryOptimisticRead();
        int readNumber = number;
        System.out.println("Happy lock read: " + readNumber);
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            System.out.println(String.format("Happy lock read number %d is wrong: , change to sad lock number: %d", readNumber, number));
            lock.unlockRead(stamp);
        }
    }
    private void write(int change) {
        long stamp = lock.writeLock();
        number += change;
        System.out.println("write " + number);
        lock.unlockWrite(stamp);
    }

    @Test
    public void test() throws InterruptedException {
        //开启一个线程修改数据
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                write(1);
            }
            System.out.println("增加 100 次已完成");
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