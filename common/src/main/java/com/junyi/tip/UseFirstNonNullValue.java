package com.junyi.tip;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @time: 2020/10/26 15:15
 * @version: 1.0
 * @author: junyi Xu
 * @description: 取字段的值，依次从data, data2, data3, defaults中获取第一个不为空的值
 */
public class UseFirstNonNullValue {

    public void process(Book book) {
        String value;
        List<String> sList = Arrays.asList(book.getData(), book.getData2(), book.getData3(), book.getDefaults());
        sList.removeIf(StringUtils::isBlank);
        value = sList.get(0);
        // do something with value
    }

    class Book {
        private Integer id;
        private String data;
        private String data2;
        private String data3;
        private String defaults;

        public String getData() {
            return data;
        }

        public String getData2() {
            return data2;
        }

        public String getData3() {
            return data3;
        }

        public String getDefaults() {
            return defaults;
        }
    }
}
