package com.junyi.controller;


import com.github.pagehelper.PageInfo;
import com.junyi.entry.BookRequest;
import com.junyi.entry.BookResponse;
import com.junyi.service.BookService;
import com.junyi.entry.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j // 同上
@RestController // SpringBoot中注册Controller Bean的注解
@RequiredArgsConstructor // 同上
public class BookController {

    @Autowired
    BookService service;

    /**
     * 分页查询
     *
     * @param pageParam 分页查询参数
     * @return 分页查询响应
     */
    @PostMapping(path = "page")
    public PageInfo<BookResponse> page(@RequestBody PageParam<BookRequest> pageParam) {
        return service.page(pageParam);
    }

    /**
     * 集合查询
     *
     * @param listParam 集合查询参数
     * @return 集合查询响应
     */
    @PostMapping(path = "list")
    public List<BookResponse> list(@RequestBody BookRequest listParam) {
        return service.list(listParam);
    }
}