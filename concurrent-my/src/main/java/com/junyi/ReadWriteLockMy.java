package com.junyi;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用读写锁实现缓存
 *
 * 注意的是，读锁不能升级为写锁，而写锁是可以降级为读锁的，这儿降级的意思是持有写锁的过程中获取读锁
 *
 * @time: 2021/4/28 10:56
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class ReadWriteLockMy<K, V> {

    final Map<K, V> m = new HashMap<>();
    final ReadWriteLock rwl = new ReentrantReadWriteLock();
    // 读锁
    final Lock r = rwl.readLock();
    // 写锁
    final Lock w = rwl.writeLock();

    // 读缓存，对于缓存中没有的数据从数据库中获取
    V get(K key) {
        V value = null;
        r.lock();
        try {
            value = m.get(key);
        } finally {
            r.unlock();
        }
        if (value != null) {
            return value;
        }
        w.lock();
        try {
            value = m.get(key);
            if (value == null) {        // 再次判断，避免高并发场景下重复查询数据库
                value = loadDatabase();
                m.put(key, value);
            }
        } finally {
            w.unlock();
        }
        return value;
    }


    // 写缓存
    V put(K key, V value) {
        w.lock();
        try {
            return m.put(key, value);
        } finally {
            w.unlock();
        }
    }

    /** 从数据库中获取数据 */
    private V loadDatabase() {
        return null;
    }


    private boolean validData;
    /** 读写锁的升级与降级 */
    public void processData() {
        V data = null;
        r.lock();
        if (!validData) {
            r.unlock();     // 不允许升级锁，所以需要先释放读锁，才能再获取写锁
            w.lock();
            try {
                if (!validData) {
                    data = loadDatabase();  // 可以从数据库或者其他接口获取数据
                    validData = true;
                }
                r.lock();       // 允许锁的降级，可以直接获取写锁
            } finally {
                w.unlock();
            }
        }
        // 执行到这儿，是持有读锁的
        try {
            // 使用data数据
        } finally {
            r.unlock();
        }
    }


}
