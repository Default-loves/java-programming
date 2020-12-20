package com.junyi.service;

import com.junyi.entry.BookRequest;
import com.junyi.entry.BookResponse;

public interface BookService extends BaseService<BookRequest, BookResponse>{

    void send();
}
