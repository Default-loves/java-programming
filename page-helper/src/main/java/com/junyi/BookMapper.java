package com.junyi;

import java.util.List;

public interface BookMapper {

    List<BookResponse> getBook(BookRequest request);
}
