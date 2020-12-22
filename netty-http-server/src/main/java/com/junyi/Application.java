package com.junyi;

import com.junyi.server.HttpServer;

/**
 * @time: 2020/10/9 10:44
 * @version: 1.0
 * @author: junyi Xu
 * @description: 基于 Netty 实现一个简单的Http Server
 */
public class Application {
    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        httpServer.start();
    }
}