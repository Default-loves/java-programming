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









