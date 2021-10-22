package com.junyi.model;

/**
 * @time: 2021/10/21 16:15
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class Name {
    String firstName;
    String lastName;

    public Name() {
    }

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
