package com.junyi.net;

import java.io.IOException;
import java.net.*;

/**
 * User: JY
 * Date: 2020/7/22 0022
 * Description: 基于UDP协议的客户端实例
 */
public class DataGrameSocketClient {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        ds.setSoTimeout(1000);
        ds.connect(InetAddress.getByName("localhost"), 6666); // 连接指定服务器和端口
        // 发送:
        byte[] data = "Hello you~".getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length);
        ds.send(packet);
        // 接收:
        byte[] buffer = new byte[1024];
        packet = new DatagramPacket(buffer, buffer.length);
        ds.receive(packet);
        String resp = new String(packet.getData(), packet.getOffset(), packet.getLength());
        System.out.println(resp);
        ds.disconnect();
    }
}
