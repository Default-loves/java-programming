package com.junyi.demo;

import org.junit.jupiter.api.Test;

/**
 * User: JY
 * Date: 2020/5/9 0009
 * Description:
 */

/**
 * 执行结果的number不一定为0，可能是其他的数字：
 * 增加 10000 次已完成
 * 减少 10000 次已完成
 * number = -23
 */
public class Client1 {
    private int number = 0;

    private void read() {
        System.out.println("number = "+ number);
    }

    private void write(int change) {
        number += change;
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