package com.junyi;

import com.junyi.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @time: 2020/9/4 11:58
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@RestController
@Slf4j
public class BookController {


    @PostMapping("/book")
    public void book(@RequestBody Book book) {
      log.info(book.toString());
    }
}
