### devops

devops帮助开发人员快速部署服务和验证问题，当通过代码仓库提交代码后，根据devops脚本自动进行maven打包、部署服务器，减少开发者手动操作



### 云服务器

本地项目通过IDEA的`Alibaba Cloud Toolkit `Plugin，可以实现自动的将项目打包好，并且传输到云服务器上。然后需要到云服务器中通过`java -jar XXX.jar`运行上传的项目



### Exception的中断操作

```java
Thread th = Thread.currentThread();
while(true) {
  if(th.isInterrupted()) {
    break;
  }
  // 省略业务代码无数
  try {
    Thread.sleep(100);
  }catch (InterruptedException e){
    e.printStackTrace();
  }
}
```

以上代码是存在问题的，当调用了th.interrupt()方法后也不会跳出while循环，因为此时方法大概率是阻塞在休眠方法的，由于中断异常会被catch掉，然后JVM会把中断标志位清除，从而判断`th.isInterrupted()`还是返回false

解决办法就是在catch代码块中重新设置中断标志位

```java
try {
    Thread.sleep(100);
  }catch (InterruptedException e){
    th.interrupt();
    e.printStackTrace();
  }
```



### Serverless

Serverless的开发模式和运行模式类似这样：

1. 程序员编写完成业务的函数代码。

2. 上传到支持Serverless的平台，设定触发的规则。

3. 请求到来，Serverless平台根据触发规则加载函数，创建函数实例，运行

4. 如果请求比较多，会进行实例的扩展，如果请求较少，就进行实例的收缩。

5. 如果无人访问，卸载函数实例。

Serverless的函数应该是无状态的，每次启动都可能被部署到新的服务器中，因此像用户的会话状态是无法保持的，而且无法做本地的持久化，没法访问本地的磁盘，所有想持久的数据需要保存到外部的存储服务（如Redis、Mysql）中

因此，Serverless更适合那些无状态的应用，例如图像和视频的加工，转换， 物联网设备状态的信息处理等等。



### APM

APM（Application Performance Management），应用行为管理系统，主要用于监控应用程序，记录业务逻辑的操作，提供定位问题的能力

一般包括三个部分，Log、Trace和Metric

- log：业务逻辑的日志，常用的组件是Elasticsearch
- Trace：请求的调用链路，常用的组件是SkyWalking
- Metric：报表的统计与展示，开源组件是Cat



