package com.junyi;

import org.junit.jupiter.api.Test;

/**
 * User: JY
 * Date: 2020/5/9 0009
     * Description: 多线程写入数据没有问题了，不过读取数据有问题
 * 读取不能读取到正确的数字
 */
public class Client3 {
    private int number = 0;
    private Object lock = new Object();

    private void read() {
        System.out.println("read number = "+ number);
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
            for (int i = 0; i < 10000; i++) {
                write(1);
            }
            System.out.println("增加 10000 次已完成");
        }).start();

        // 开启一个线程读取数据
        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                read();
            }
        }).start();

        // 睡眠一秒保证线程执行完成
        Thread.sleep(1000);
        // 读取结果
    }
}