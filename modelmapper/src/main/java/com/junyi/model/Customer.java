package com.junyi.model;

/**
 * @time: 2021/10/21 16:14
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class Customer {
    Name name;

    public Customer() {
    }

    public Customer(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
}
