package com.junyi.service;

import com.junyi.mapper.BookMapper;
import com.junyi.entry.BookRequest;
import com.junyi.entry.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookServiceImpl implements BookService{

    @Autowired
    BookMapper bookMapper;

    @Override
    public void send() {

    }

    @Override
    public List<BookResponse> list(BookRequest bookRequest) {
        return bookMapper.getBook(bookRequest);
    }
}
