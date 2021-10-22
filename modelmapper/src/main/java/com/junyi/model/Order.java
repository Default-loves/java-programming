package com.junyi.model;

/**
 * @time: 2021/10/21 16:14
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class Order {
    Customer customer;
    Address billingAddress;


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }
}