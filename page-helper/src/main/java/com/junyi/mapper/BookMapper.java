package com.junyi.mapper;

import com.junyi.entry.BookRequest;
import com.junyi.entry.BookResponse;

import java.util.List;

public interface BookMapper {

    List<BookResponse> getBook(BookRequest request);
}
