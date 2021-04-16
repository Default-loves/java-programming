package com.junyi;

/**
 * @time: 2021/4/15 9:31
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class RedisLockTest {
    private static RedisLockUtil lockUtil = new RedisLockUtil();
    private static Integer NUM = 101;

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                String id = Thread.currentThread().getId() + "";
                boolean isLock = lockUtil.lock(id);
                try {
                    // 拿到锁的话，就对共享参数减一
                    if (isLock) {
                        NUM--;
                        System.out.println(NUM);
                    }
                } finally {
                    // 释放锁一定要注意放在finally
                    lockUtil.unlock(id);
                }
            }).start();
        }
    }
}
