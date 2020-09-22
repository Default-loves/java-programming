package com.junyi.basis;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Optional;

/**
 * User: JY
 * Date: 2020/7/27 0027
 * Description: Optional的使用
 * @see java.util.Optional
 */
public class OptionalDemo {
    public static void main(String[] args) {
        createOptional(new Book());
        useOptional(new Book());
    }

    // 使用Optional
    private static String useOptional(Book book) {
        // if...else 的麻烦做法
        /*
        if (book != null) {
            String name = book.getName();
            if (name != null) {
                return name.toUpperCase();
            } else {
                return null;
            }
        } else {
            return null;
        }
         */

        // 使用Optional以函数式编程的方式，能够简化代码，不必进行NullPoint的判断
        Optional<Book> bookOptional = Optional.ofNullable(book);
        return bookOptional.map(Book::getName)
                .map(String::toUpperCase)
                .orElse(null);

    }

    // 创建Optional
    private static void createOptional(Book book) {
        // 创建一个空的
        Optional<String> empty = Optional.empty();
        //如果值为字符串或者认为其不会为空
        Optional<String> opt1 = Optional.of("apple");
        //如果其值可能为空
        Optional<Book> optBook = Optional.ofNullable(book);
    }


}
