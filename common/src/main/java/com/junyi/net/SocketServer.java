package com.junyi.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * User: JY
 * Date: 2020/7/22 0022
 * Description: Socket 服务端实例，，Socket基于TCP协议
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8787); // 监听指定端口
        System.out.println("server is running...");
        for (; ; ) {
            Socket sock = ss.accept();
            System.out.println("connected from " + sock.getRemoteSocketAddress());
            Thread t = new Handler(sock);
            t.start();
        }
    }

    static class Handler extends Thread {
        Socket sock;

        public Handler(Socket sock) {
            this.sock = sock;
        }

        @Override
        public void run() {
            try (InputStream input = this.sock.getInputStream()) {
                try (OutputStream output = this.sock.getOutputStream()) {
                    handle(input, output);
                }
            } catch (Exception e) {
                try {
                    this.sock.close();
                } catch (IOException ioe) {
                }
                System.out.println("client disconnected.");
            }
        }

        private void handle(InputStream input, OutputStream output) throws IOException {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            writer.write("hello\n");
            writer.flush();
            for (; ; ) {
                String s = reader.readLine();
                System.out.println("Receive message: " + s);
                if (s.equals("bye")) {
                    writer.write("bye\n");
                    writer.flush();
                    break;
                }
                writer.write("ok: " + s + "\n");
                writer.flush();
            }
        }
    }

}