基于Netty实现一个简单的Http Server



### 介绍
Netty 是一个基于 NIO 的 client-server(客户端服务器)框架，使用它可以快速简单地开发网络应用程序。

Netty 极大地简化并优化了 TCP 和 UDP 套接字服务器等网络编程,并且性能以及安全性等很多方面都要更好。

Netty 支持多种协议 如 FTP，SMTP，HTTP 以及各种二进制和基于文本的传统协议。本文所要写的 HTTP Server 就得益于 Netty 对 HTTP 协议（超文本传输协议）的支持。

### 使用

GET：http://localhost:12345/user?name=apple&age=18

![image-20201009112609279](F:\GithubMy\my\Java-Programming\http-server-netty\pic\image-20201009112609279.png)

POST：http://localhost:12345/user

![image-20201009112813270](F:\GithubMy\my\Java-Programming\http-server-netty\pic\POST.png)