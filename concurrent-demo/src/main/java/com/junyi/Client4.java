package com.junyi;

import org.junit.jupiter.api.Test;

/**
 * User: JY
 * Date: 2020/5/9 0009
 * Description: 对于Client3的数据读取的问题，需要等待所有的写入完毕后才读取
 *
 */
public class Client4 {
    private int number = 0;
    private Object lock = new Object();
    private boolean writeComplete;

    private void read() {
        synchronized (lock) {
            while (!writeComplete) {
                try {
                    System.out.println("read wait");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        System.out.println("read number = "+ number);
        }
    }

    private void write(int change) {
        synchronized (lock) {
            number += change;
        }
        System.out.println("write number =" + number);
    }

    @Test
    public void test() throws InterruptedException {
        //开启一个线程修改数据
        new Thread(() -> {
            writeComplete = false;
            for (int i = 0; i < 100; i++) {
                write(1);
            }
            System.out.println("增加 100 次已完成");
            writeComplete = true;
            synchronized (lock) {
                lock.notifyAll();
            }
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