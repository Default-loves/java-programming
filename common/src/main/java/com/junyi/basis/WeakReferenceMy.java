package com.junyi.basis;

import com.junyi.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2021/1/20 19:33
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class WeakReferenceMy {

    class WeakRefrenceBook extends WeakReference<Book> {

        public WeakRefrenceBook(Book referent) {
            super(referent);
        }
    }

    @Test
    public void test() {
        Book book = Book.builder()
                .id(1)
                .name("junyi")
                .build();
        WeakRefrenceBook weakRefrenceBook = new WeakRefrenceBook(book);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(() -> {
            log.info("Ready to gc");
            System.gc();
        }, 10, TimeUnit.SECONDS);
        int cnt = 0;
        while (true) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (weakRefrenceBook.get() != null) {
                cnt++;
                log.info("loop: {}, still in memory：{}", cnt, weakRefrenceBook.get().toString());
            } else {
                log.info("end");
                break;
            }
        }
    }
}
