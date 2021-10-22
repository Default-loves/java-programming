





##### 设置

```shell

options json-format true
```



###### 启动

```shell
curl -O https://arthas.aliyun.com/arthas-boot.jar
java -jar arthas-boot.jar
```

###### 全局

dashboard

###### 线程

```shell
# 线程面板
thread
# 显示全部
thread -all
# 指定线程号
thread THREAD_ID
# 显示最繁忙的3个线程
thread -n 3
# 当前阻塞其他线程的线程，目前只支持找出synchronized关键字阻塞住的线程
thread -b

```



###### 查看源码

```shell
# 查看类
jad com.junyi.MyController
# 查看指定的方法
jad com.junyi.MyController buy
```



###### 监视方法

watch com.junyi.MyController bug returnObj



###### 方法调用追踪

- 基本：`trace demo.MathGame run`

- 过滤，只显示耗时大于10毫秒的：`trace demo.MathGame run '#cost > 10'`

**动态trace，支持动态修改trace的目标**

```shell
由于trace只会追踪到指定方法的下一层，不会显示全部的调用，因为消耗更高，但是可以在运行的时候，动态修改追踪的目标
# 记住listenerId: 1
$ trace demo.MathGame run
Press Q or Ctrl+C to abort.
Affect(class count: 1 , method count: 1) cost in 112 ms, listenerId: 1

# 再开启一个终端，连接上arthas
telnet localhost 3658

# 指定listener
trace demo.MathGame primeFactors --listenerId 1

# 回到原来的终端，可以发现追踪多了一层
```



###### 退出

exit / quit 只是退出界面，仍然存在连接，完全退出请使用stop

###### JVM信息

```shell
> jvm
```

THREAD相关

- COUNT: JVM当前活跃的线程数
- DAEMON-COUNT: JVM当前活跃的守护线程数
- PEAK-COUNT: 从JVM启动开始曾经活着的最大线程数
- STARTED-COUNT: 从JVM启动开始总共启动过的线程次数
- DEADLOCK-COUNT: JVM当前死锁的线程数

文件描述符相关

- MAX-FILE-DESCRIPTOR-COUNT：JVM进程最大可以打开的文件描述符数
- OPEN-FILE-DESCRIPTOR-COUNT：JVM当前打开的文件描述符数

###### JVM系统属性

```shell
sysprop
# 查看指定的项
sysprop java.home
```

###### JVM环境变量

sysenv

###### JVM诊断相关的option

vmoption

###### 日志

```shell
logger

# 更新logger level
logger --name ROOT --level debug
```

###### 堆转储

heapdump /tmp/dump.hprof



##### class/classLoader相关

###### 查看已加载的类信息

sc -d -f demo.MathGame







### 例子

#### 热更新代码

```shell
# 反编译代码或者直接将修改的.java文件放置到服务器上
jad --source-only com.example.demo.arthas.user.UserController > /tmp/UserController.java

# 编辑代码

# sc查找加载UserController的ClassLoader
$ sc -d *UserController | grep classLoaderHash
 classLoaderHash   1be6f5c3

# mc内存编绎代码
$ mc -c 1be6f5c3 /tmp/UserController.java -d /tmp
Memory compiler output:
/tmp/com/example/demo/arthas/user/UserController.class
Affect(row-cnt:1) cost in 346 ms

# redefine热更新代码
$ redefine /tmp/com/example/demo/arthas/user/UserController.class
redefine success, size: 1
```

