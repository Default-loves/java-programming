Docker

### 常用组件的启动命令
```shell
# redis
docker run -p 6380:6379 --name redis -v /mydata/redis/data:/data -d redis:latest redis-server --appendonly yes

# rabbitMQ：
docker run -it -d --name rabbitmq-my -p 5672:5672 -p 15672:15672 rabbitmq:3-management

# mysql：
docker run --name mysql-jy -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql

# mongo
docker run --name mongo -p 27017:27017 -v D:/myDocker/docker-data/mongo:/data/db -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=admin -d mongo

# zookeeper
docker run --name zookeeper-my -p 2181:2181 --restart always -d zookeeper

# kafka(需要先启动zookeeper)
docker run  -d --name kafka-my -p 9092:9092 
-e KAFKA_BROKER_ID=0 
-e KAFKA_ZOOKEEPER_CONNECT=172.16.40.106:2181 
-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://172.16.40.106:9092 
-e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 
-t wurstmeister/kafka


# elasticsearch
docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.11.1

# kibana

# postgreSQL
docker run -d -p 5432:5432 --name postgresql -e POSTGRES_PASSWORD=123456 postgres

# Redis
docker run -itd --name redis-test -p 6379:6379 redis

```


### 容器
容器是一种轻量级、可移植、自包含的软件打包技术，使应用程序几乎可以在任何地方以相同的方式运行
- 容器与容器之间保证了进程级的隔离级别，使一个物理机中不同的容器操作之间不会有所影响
- 容器保证了开发环境，测试环境和发布环境的统一化，大大减少了部署环境的消耗时间
- Docker主要解决环境配置问题，使用Docker可以不修改应用程序代码，不需要开发人员学习特定环境下的技术，就能够将应用程序部署在其他机器上
- Docker是一种虚拟化技术，对进程进行隔离，被隔离的进程独立于宿主操作系统和其他隔离的进程

### 与虚拟机的区别
- 实现方式：虚拟机也是一种虚拟化技术，虚拟机是通过模拟硬件，并在硬件上安装操作系统来实现；而Docker是一个进程
- 启动速度：虚拟机需要启动模拟的操作系统，然后才可以启动应用，这个过程很慢；而启动Docker相当于启动宿主操作系统的一个进程
- 资源占用：虚拟机是一个完整地操作系统，需要占用大量的资源；而Docker是一个进程，在运行时占用很少的资源

### 优势
- 启动速度快，占用资源少
- 更容易迁移：提供一致性的运行环境，已经打包好的应用程序可以在不同机器上迁移，而不用担心环境的不同导致程序不能运行
- 更容易维护：使用分层技术和镜像，使得应用可以更容易复用重复的部分
- 更容易扩展：使用基础镜像可以进一步扩展得到新的镜像

### 应用场景
- 持续集成：持续集成指的是频繁地将代码集成到主干上，这样能够更快发现错误，Docker具有隔离性和轻量级的特点，在将代码集成到一个Docker中不会对其他Docker产生影响
- 搭建微服务架构：Docker轻量级的特点使其适合部署、维护、组合微服务
- 搭建可伸缩的云服务：根据应用的负载情况，可以很容易地增加/减少Docker

### 镜像和容器
镜像是静态的结构，相当于面向对象的类，而容器相当于镜像的一个实例

镜像包括了应用程序运行的全部代码和依赖组件，镜像是分层的，一层层构建，高层依赖底层，并且是只读的，镜像的分层特性使其能够容易复用

容器会在镜像的基础之上构建一层可写层(writable layer)，用来保存容器运行过程中的修改

### 安装Docker

#### Linux

```shell
curl -fsSL get.docker.com -o get-docker.sh
sh get-docker.sh --mirror Aliyun
// 启动docker
systemctl start docker
// 开机自动启动
systemctl enable docker
```

### 使用

镜像：
- docker pull <image>
- docker search <image>
- 备份镜像：`docker save my_tomcat:1.0 -o my-tomcat-1.0.tar`
- 加载备份的镜像：`docker load -i my-tomcat-1.0.tar`

容器：
- docker run
- docker start/stop CONTAINER_NAME
- docker ps CONTAINER_NAME
- 查看容器的日志：docker logs -ft 289cc00dc5ed。-f是实时显示，-t是显示时间戳
- 查看容器内部细节：docker inspect 923c969b0d91
- 与容器交互：docker exec -it 289cc00dc5ed bash
- 文件传输：docker cp 289cc00dc5ed:/usr/local/test.log /home/

**docker run 中的参数设置**

- -d: 后台运行容器，并返回容器ID；
- -i: 以交互模式运行容器，通常与 -t 同时使用；
- -P: 随机端口映射，容器内部端口随机映射到主机的端口
- -p: 指定端口映射，格式为：主机(宿主)端口:容器端口
- -t: 为容器重新分配一个伪输入终端，通常与 -i 同时使用；
- --name 制定容器名称
- -e 设置环境变量
- --expose/-p 宿主端口：容器端口
- -v 宿主目录：容器目录，挂载磁盘卷

### 数据卷

容器的运行命令docker run -v source:target

会自动将source目录下的文件复制到target目录下，实现宿主机和容器之间文件的共享，其中的source可以是文件路径，比如：`docker run -d -p 8080:8080 --name tomcat01 -v /opt/apps:/usr/local/tomcat/webapps tomcat:8.0-jre8`

也可以自定义数据卷的名字，比如：`docker run -d -p 8080:8080 --name tomcat01 -v aa:/usr/local/tomcat/webapps tomcat:8.0-jre8`

会在目录`/var/lib/docker/volumes`创建一个数据卷aa，将aa与容器中的目录文件共享



### 运行java程序

Docker的内存、CPU等资源是通过CGroup(Control Group)实现的，早期的JDK并不能识别这些东西，导致以下问题：
- jvm的堆和元数据区、直接内存等配置超过了容器的限制，最终被容器OOM kill，或者自身发生OOM
- 错误判断了CPU等资源，例如JVM可能设置了不合适的并行GC线程数
- 很多工具比如jcmd、jstack等会依赖“/proc//”下面提供的信息，但是Docker的设计改变了这个信息的结构，需要对原来的工具进行重新设计

对于JDK 9、10，添加对Docker容器的支持，默认会自适应各种资源限制和实现差异


### 加速
Settings -> Docker Engine

添加以下内容：
```json
{
  "registry-mirrors": [
    "https://hub-mirror.c.163.com",
    "https://mirror.baidubce.com"
  ]
}
```

另外，登陆`https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors`，获取阿里云镜像加速地址


验证：`docker info`，查看是否有相应的mirrors内容


### 通过Dockerfile安装
创建Dockerfile文件，文件内容如下
```shell
FROM ubuntu:16:04

RUN apt-get update

RUN apt-get install -y vim

CMD ["sh"]
```

安装：`$docker build -t my-awesome-linux .`

运行：`$docker run -it my-awesome-linux bash`