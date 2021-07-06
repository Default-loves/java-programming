package com.junyi;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 模拟 Redis Client 向 Redis Server 发送原生的请求，需要按照协议发送消息
 * @time: 2021/6/25 9:22
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class Client {

    public static void main(String[] args) throws IOException {

        String CRLF = "\r\n";

        Socket socket = new Socket("localhost", 6379);
        try(OutputStream outputStream = socket.getOutputStream()) {
            final StringBuilder sb = new StringBuilder();
            sb.append("*3").append(CRLF);
            sb.append("$3").append(CRLF).append("set").append(CRLF);
            sb.append("$4").append(CRLF).append("type").append(CRLF);
            sb.append("$9").append(CRLF).append("123456789").append(CRLF);

            outputStream.write(sb.toString().getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            try(final InputStream inputStream = socket.getInputStream()) {
                byte[] buffer = new byte[1024];
                int len = inputStream.read(buffer);
                if (len > 0) {
                    final String recv = new String(buffer, 0, len);
                    log.info("【receive: {}】", recv);
                }
            }
        }
    }
}
