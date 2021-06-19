package com.junyi.entity;

/**
 * @time: 2021/1/14 10:13
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class CommonResponse<T> {

    public static <T> CommonResponse<T> success(T t) {
        return new CommonResponse<>();
    }
}
