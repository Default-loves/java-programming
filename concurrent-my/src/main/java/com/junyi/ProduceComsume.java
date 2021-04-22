package com.junyi;

import com.google.common.util.concurrent.RateLimiter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * 生产者消费者模型
 * 多个生产者添加日志，一个消费者消费日志批量刷盘：
 * 刷盘的条件：
 * 1. 遇到 Error 日志
 * 2. 距离上次刷盘超过5秒
 * 3. 累计日志超过500条
 * @time: 2021/4/19 15:59
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class ProduceComsume {

    private static final BlockingDeque<LogMsg> bq = new LinkedBlockingDeque<>();
    private static final ExecutorService es = Executors.newFixedThreadPool(1);
    public static final int THRESHOLD = 500;    // 日志累计上限，达到值后需要进行刷盘

    public void start() throws IOException {
        File file = File.createTempFile("temp", ".log");
        FileWriter fw = new FileWriter(file);
        es.execute(() -> {
            int count = 0;
            long now = System.currentTimeMillis();
            while (true) {
                try {
                    LogMsg record = bq.poll(5, TimeUnit.SECONDS);
                    if (record != null) {
                        count++;
                        fw.write(record.getMsg());
                    }
                    // 判断是否刷盘
                    if (record != null && record.level == Level.ERROR
                            || count >= THRESHOLD
                            || System.currentTimeMillis() - now > 5000) {
                        fw.flush();
                        count = 0;
                        now = System.currentTimeMillis();
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fw.flush();
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void addInfo(String msg) {
        bq.add(new LogMsg(Level.INFO, msg));
    }

    public void addError(String msg) {
        bq.add(new LogMsg(Level.ERROR, msg));
    }


    enum Level {
        INFO, ERROR
    }

    class LogMsg {
        private Level level;
        private String msg;

        public LogMsg() {
        }

        public LogMsg(Level level, String msg) {
            this.level = level;
            this.msg = msg;
        }

        public Level getLevel() {
            return level;
        }

        public void setLevel(Level level) {
            this.level = level;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
