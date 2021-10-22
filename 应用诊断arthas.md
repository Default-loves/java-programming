

###### 启动

```shell
curl -O https://arthas.aliyun.com/arthas-boot.jar
java -jar arthas-boot.jar
```

###### 进程面板

dashboard

##### 线程

thread THREAD_ID

###### 反编译

jad com.junyi.Application

###### 方法追踪

watch com.junyi.MyController click returnObj

###### 退出

quit / exit会退出arthas，但是还保持着连接，

