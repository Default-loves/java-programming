package com.junyi;

/**
 * @time: 2021/3/26 11:49
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */

public class CallBackMyImpl implements CallBackMy {
    @Override
    public void handle(String message) {
        System.out.println("Receive message: " + message);
    }
}
