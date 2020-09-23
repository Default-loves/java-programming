package com.junyi.basis;

/**
 * @time: 2020/9/22 16:19
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class Book {
    private Integer id;
    private String name;

    public Book() {
    }

    public Book(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
