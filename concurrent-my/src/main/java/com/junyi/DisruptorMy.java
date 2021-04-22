package com.junyi;

import org.junit.jupiter.api.Test;
import reactor.jarjar.com.lmax.disruptor.RingBuffer;
import reactor.jarjar.com.lmax.disruptor.dsl.Disruptor;
import reactor.jarjar.com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**Disruptor 是高性能的有界内存队列，性能比Java SDK提供的BlockingQueue性能高
 * Disruptor特点：
 * 1. 内存分配更加合理，使用 RingBuffer 数据结构，数组元素在初始化时一次性全部创建，提升缓存命中率；对象循环利用，避免频繁 GC。
 * 2. 能够避免伪共享，提升缓存利用率。伪共享的意思是Cache中多个变量共享缓存行导致的缓存无效的问题，一般的解决办法是一个变量独占一个缓存行，具体是缓存行填充
 * 3. 采用无锁算法，避免频繁加锁、解锁的性能消耗。
 * 4. 支持批量消费，消费者可以无锁方式消费多个消息。
 * @time: 2021/4/21 10:15
 * @version: 1.0
 * @author: junyi Xu
 */
public class DisruptorMy {

    @Test
    public void test() throws InterruptedException {
        //指定RingBuffer大小,
        //必须是2的N次方
        int bufferSize = 1024;

        //构建Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(
                LongEvent::new,
                bufferSize, new ThreadPoolExecutor(3, 10, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000)));

        //注册事件处理器
        disruptor.handleEventsWith(
                (event, sequence, endOfBatch) -> System.out.println("E: " + event));

        //启动Disruptor
        disruptor.start();

        //获取RingBuffer
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        //生产Event
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            //生产者生产消息
            ringBuffer.publishEvent(
                    (event, sequence, buffer) ->
                            event.set(buffer.getLong(0)), bb);
            Thread.sleep(1000);
        }
    }

    //自定义Event
    class LongEvent {
        private long value;
        public void set(long value) {
            this.value = value;
        }
    }
}
