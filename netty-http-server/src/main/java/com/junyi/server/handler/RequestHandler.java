package com.junyi.server.handler;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @time: 2020/10/9 11:01
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public interface RequestHandler {
    Object handle(FullHttpRequest fullHttpRequest);
}