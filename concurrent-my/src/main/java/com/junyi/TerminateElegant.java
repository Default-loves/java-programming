package com.junyi;

/**
 * 优雅地关闭线程
 * @time: 2021/4/19 14:24
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class TerminateElegant {

    volatile boolean terminate = false;     // 中断标志位
    boolean started = false;        // 保证只有一个线程在运行
    Thread thread;      // 执行任务的线程


    public synchronized void start() {
        if (started) {
            return;
        }
        started = true;
        terminate = false;
        thread = new Thread(() -> {
            while (!terminate) {
                sendRecord();
                try {
                    Thread.sleep(2_000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();     // 这儿重新设置了线程的中断状态，因为JVM捕获异常的时候，会清除掉中断状态
                }
            }
            started = false;
        });
        thread.start();
    }


    public synchronized void stop() {
        thread.interrupt();
        terminate = true;
    }

    /** 发送记录 */
    private void sendRecord() {
    }
}
