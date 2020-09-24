package com.junyi;


import com.junyi.entity.Book;

import java.util.Optional;

/**
 * User: JY
 * Date: 2019/1/23 0023
 * Description:
 */



public class aaa {
    public static void main(String[] args) {
        Book book = new Book();
        Double total = 10.0;
        total += Optional.ofNullable(book).map(Book::getPrice).orElse(0.0);
        System.out.println(total);
    }
}
