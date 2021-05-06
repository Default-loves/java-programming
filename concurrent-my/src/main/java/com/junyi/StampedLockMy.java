package com.junyi;

import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock 是 ReadWriteLock的子集，在读多写少的场景除了读锁和写锁，还提供了乐观读，性能比 ReadWriteLock 性能更好，注意的是乐观读操作是无锁操作
 *
 * 注意事项：
 * 1. 如果要中断获取了读锁和写锁的线程，请使用相应的 XXXLockInterruptibly方法获取锁，否则会导致CPU 100%
 * 2. 读锁和写锁不支持Lock的条件变量
 * @time: 2021/4/28 11:37
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class StampedLockMy {


    final StampedLock sl = new StampedLock();
    private Object data;


    /** 乐观读 */
    public Object read() {
        // 乐观读获取戳
        long stamp = sl.tryOptimisticRead();
        // 获取数据
        Object obj = data;
        // 判断数据是否存在更新操作
        if (!sl.validate(stamp)) {
            // 升级为悲观读锁
            stamp = sl.readLock();
            try {
                obj = data;
            } finally {
                sl.unlockRead(stamp);
            }
        }
        return obj;
    }

    public void write() {
        long stamp = sl.writeLock();
        try {
            // 更新数据
        } finally {
            sl.unlockWrite(stamp);
        }
    }


}
