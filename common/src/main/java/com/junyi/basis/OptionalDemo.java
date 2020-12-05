package com.junyi.basis;

import com.junyi.entity.Book;
import lombok.Data;

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

    /** use Optional to avoid NullPointException */
    private static String useOptional(Book book) {
        /** if...else 的麻烦做法 */
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
        return Optional.ofNullable(book)
                .map(Book::getName)
                .map(String::toUpperCase)
                .orElse(null);
    }

    // 创建Optional
    private static void createOptional(Book book) {
        // 创建一个空的
        Optional<String> empty = Optional.empty();
        // 如果值为字符串或者认为其不会为空
        Optional<String> opt1 = Optional.of("apple");
        // 如果其值可能为空
        Optional<Book> optBook = Optional.ofNullable(book);
    }

    /** 一个 JSON 数据层次性比较深，可能有4,5层，需要对里层的数据进行获取，为了避免空指针，使用 Optional，否则需要对集合类型对象逐一判断是否为null */
    public String[] getData(Outer outer) {
        return Optional.ofNullable(outer)
                .map(Outer::getMiddle)
                .map(Middle::getInner)
                .map(o -> new String[]{String.valueOf(o.getInnerId()), o.getName()})
                .orElse(null);
    }

    @Data
    class Outer {
        private Integer id;
        private Middle middle;
    }
    @Data
    class Middle {
        private Integer midId;
        private Inner inner;
    }
    @Data
    class Inner {
        private Integer innerId;
        private String name;
    }


}
