package com.junyi;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用 ReentrantLock 实现阻塞队列
 * @time: 2021/4/25 15:27
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class ReentranceLockMy {


    public class BlockedQueue<T>{
        final Lock lock = new ReentrantLock();
        // 条件变量：队列不满
        final Condition in = lock.newCondition();
        // 条件变量：队列不空
        final Condition out = lock.newCondition();
        public final List<String> queue = new LinkedList<>();
        public final Integer size = 100;

        // 入队
        void enq(T x) {
            lock.lock();
            try {
                while (queue.size() >= size){       // 队列已满
                    // 等待队列不满
                    in.await();
                }
                // 省略入队操作...
                //入队后,通知可出队
                out.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        // 出队
        void deq(){
            lock.lock();
            try {
                while (queue.isEmpty()){   // 队列已空
                    // 等待队列不空
                    out.await();
                }
                // 省略出队操作...
                //出队后，通知可入队
                in.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
