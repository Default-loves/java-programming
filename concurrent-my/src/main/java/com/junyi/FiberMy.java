package com.junyi;

import co.paralleluniverse.fibers.Fiber;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.locks.LockSupport;

/**
 * Java 轻量级线程, 性能比 Thread 快很多，相当于其他语言的协程
 * @time: 2021/4/16 16:07
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class FiberMy {

    public static void main(String[] args) throws IOException {
        final ServerSocketChannel ssc = ServerSocketChannel.open().bind(new InetSocketAddress(8080));
        //处理请求
        try {
            while (true) {
                // 接收请求
                final SocketChannel sc = ssc.accept();
                Fiber.currentFiber().getScheduler().getExecutor().execute(() -> {
                    try {
                        // 读Socket
                        ByteBuffer rb = ByteBuffer
                                .allocateDirect(1024);
                        sc.read(rb);
                        //模拟处理请求
                        LockSupport.parkNanos(2000 * 1000000);
                        // 写Socket
                        ByteBuffer wb = (ByteBuffer) rb.flip();
                        sc.write(wb);
                        // 关闭Socket
                        sc.close();
                    } catch (Exception e) {
                        throw new UncheckedIOException((IOException) e);
                    }
                });
            }
        } finally {
            ssc.close();
        }

    }
}
