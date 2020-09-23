package com.junyi.main;


import com.junyi.child1.spi.HelloService;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Application {

    public static void main(String[] args)  {
        ServiceLoader<HelloService> serviceLoader = ServiceLoader.load(HelloService.class);
        Iterator<HelloService> it = serviceLoader.iterator();
        String msg = "Test SPI";
        while (it.hasNext()) {
            HelloService s = it.next();
            System.out.println(String.format("class: %s, result: %s ", s.getClass().getName(), s.f(msg)));
        }
    }
}
