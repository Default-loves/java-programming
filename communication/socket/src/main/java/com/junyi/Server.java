package com.junyi;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @time: 2021/6/25 9:30
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(6378);
        log.info("【wait...】");

        final Socket socket = serverSocket.accept();
        try(final InputStream inputStream = socket.getInputStream()) {
            final byte[] buffer = new byte[1024];
            while (inputStream.read(buffer) != 0) {
                log.info("【receive: {}】", new String(buffer));
            }
        }
    }
}
