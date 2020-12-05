package com.junyi.demo;

import org.junit.jupiter.api.Test;

/**
 * User: JY
 * Date: 2020/5/9 0009
 * Description: 为了解决client1的问题，使用synchronize
 * 执行结果的number为0
 */
public class Client2 {
    private int number = 0;
    private Object object = new Object();

    private void read() {
        System.out.println("number = "+ number);
    }

    private void write(int change) {
        synchronized (object) {
            number += change;
        }
    }

    @Test
    public void test() throws InterruptedException {
        // 开启一个线程加 10000 次
        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                write(1);
            }
            System.out.println("增加 10000 次已完成");
        }).start();

        // 开启一个线程减 10000 次
        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                write(-1);
            }
            System.out.println("减少 10000 次已完成");
        }).start();

        // 睡眠一秒保证线程执行完成
        Thread.sleep(1000);
        // 读取结果
        read();
    }
}