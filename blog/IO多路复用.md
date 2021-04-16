

### 阻塞IO

阻塞IO的写法大概如下所示

```java
fd = socket();	// 通过Socket开启网络通道
bingd(fd);		// 绑定端口
listen(fd);		// 监听端口
while(true) {
	   connection = accept(fd);		// 阻塞接收客户端的请求
    data = read(connection);	// 阻塞读取数据
    process(data);		// 处理接收的数据
    close(connection);		// 关闭连接
}
```

使用一个线程来接收客户端的请求以及读取数据，这当中会有两个阻塞的地方，一个是建立连接，一个是数据读取，当进行客户端1的读取数据操作时，是无法处理客户端2的请求的





### 非阻塞IO

我们注意到阻塞IO中，只使用了一个线程来处理，我们可以使用多线程来将两个阻塞操作分开，伪代码如下：

```java
fd = socket();	// 通过Socket开启网络通道
bingd(fd);		// 绑定端口
listen(fd);		// 监听端口

while(true) {
	   connection = accept(fd);		// 阻塞接收客户端的请求
    new_thread(process(connection))		// 创建新线程执行读取数据h和其他操作
}
process(connection) {
		 data = read(connection);	// 阻塞读取数据
    process(data);		// 处理接收的数据
    close(connection);		// 关闭连接    
}

```

这个能够叫非阻塞IO吗？我觉得不能。

我们只是将read操作放到单独的线程中执行，使得接收客户端的请求和读数据操作分离开，两者不会有耦合而已，即处理请求能够更加丝滑，不会因为读取数据而阻塞

不过操作系统执行的read操作仍然是阻塞的，而且这个操作是最耗时的，包括以下的几个步骤：

1. 网卡接收数据，放置到内核缓冲区中
2. 等待数据都接收完毕后，从内核缓冲区复制到用户缓冲区
3. 调用系统命令read读取数据

通常的read()操作，需要按顺序执行完以上的3个步骤，这是阻塞IO，而操作系统也能够提供非阻塞IO，当数据未就绪的时候，返回-1，那么我们可以这样使用

```java
while(true) {
    for(fd: fds) {
        if(read(fd) != -1) {
            process(data);
        }  
    }
}
```

### IO多路复用

包括三个：select、poll、epoll

#### select

select是将一组文件描述符发送给操作系统，操作系统去遍历数组，将就绪的文件描述符做上标识，返回修改后的文件描述符，我们根据标识来判断是否可以读取数据，伪代码如下：

```java
// 一个线程处理用户请求，将每个请求添加到数组中
while(1) {
  connfd = accept(listenfd);
  fcntl(connfd, F_SETFL, O_NONBLOCK);
  fdlist.add(connfd);
}

// 另一个线程遍历数据，调用select来判断是否可以读取
while(1) {
  nready = select(list);
  // 用户层依然要遍历，只不过少了很多无效的系统调用
  for(fd <-- fdlist) {
    if(fd != -1) {
      // 只读已就绪的文件描述符
      read(fd, buf);
      // 总共只有 nready 个已就绪描述符，不用过多遍历
      if(--nready == 0) break;
    }
  }
}
```

**select 的问题**

1. select 调用需要传入 fd 数组，需要拷贝一份到内核，高并发场景下这样的拷贝消耗的资源是惊人的。（可优化为不复制）

2. select 在内核层仍然是通过遍历的方式检查文件描述符的就绪状态，是个同步过程，只不过无系统调用切换上下文的开销。（内核层可优化为异步事件通知）

3. select 仅仅返回可读文件描述符的个数，具体哪个可读还是要用户自己遍历。（可优化为只返回给用户就绪的文件描述符，无需用户做无效的遍历）

#### poll

poll也是操作系统提供的函数，相比较于select，去除了只能遍历1024个文件描述符的限制

#### epoll

epoll是集大成者，解决了上述3个select的问题和poll的问题

1. 内核中保存一份文件描述符集合，无需用户每次都重新传入，只需告诉内核修改的部分即可。

2. 内核不再通过轮询的方式找到就绪的文件描述符，而是通过异步 IO 事件唤醒。

3. 内核仅会将有 IO 事件的文件描述符返回给用户，用户也无需遍历整个文件描述符集合。