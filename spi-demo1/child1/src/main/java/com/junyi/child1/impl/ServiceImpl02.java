package com.junyi.child1.impl;

import com.junyi.child1.spi.HelloService;

public class ServiceImpl02 implements HelloService {
    public String f(String msg) {
        return "ServiceImpl02 " + msg;
    }
}
