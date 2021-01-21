### Nacos作为配置中心的使用方法



Nacos实现多环境配置的方案有三个：

1、用命名空间（namespace）来区分不同的环境，一个命名空间对应一个环境；

2、用配置组（group）来区分不同的环境，命名空间用默认的public即可，一个组对应一种环境；

3、用配置集ID（Data ID）名称来区分不同的环境，命名空间和组用默认的即可，通过文件命名来区分；

推荐使用命名空间的方式，能够将不同应用的环境分隔开，在一个命名空间中只放置一个应用的配置（包括测试，开发等）



### 一、Data ID

Data ID的命名格式如下所示：

```
 ${prefix}-${spring.profiles.active}.${file-extension}
```

- prefix 默认为 spring.application.name 的值，也可以通过配置项 spring.cloud.nacos.config.prefix来配置
- spring.profiles.active 即为当前环境对应的 profile，详情可以参考 Spring Boot文档。注意：当 spring.profiles.active 为空时，对应的连接符 - 也将不存在，dataId 的拼接格式变成  ${prefix}.{file-extension}
- file-exetension 为配置内容的数据格式，可以通过配置项 spring.cloud.nacos.config.file-extension 来配置。目前只支持 properties 和 yaml 类型。

我们在Nacos中配置food-dev.properties的配置文件，内容为：`food.name= apple`

我们在Nacos中配置food-test.properties的配置文件，内容为：`food.name= durian`

运行下面的命令使用 dev 的配置文件，然后通过postman请求Restful接口，结果返回的是“apple”

```json
java -Dspring.profiles.active=dev -jar example-0.0.1-SNAPSHOT.jar
```



### 二、Group

使用Nacos中配置 Group 为 TEST_GROUP 的配置文件，比如XXX.properties

```json
java -Dspring.cloud.nacos.config.group=TEST_GROUP -jar example-0.0.1-SNAPSHOT.jar
```

这儿注意的是，文件名应该是XXX-test.properties

```json
java -Dspring.profiles.active=test -Dspring.cloud.nacos.config.group=TEST_GROUP -jar example-0.0.1-SNAPSHOT.jar
```

### 三、namespace

现在Nacos中新建命名空间 ns-dev

在“配置管理”->“配置列表”中切换新建的命名空间 ns-dev

在切换后的命名空间标签页中新建配置文件

```json
java -Dspring.cloud.nacos.config.namespace=ns_dev -jar example-0.0.1-SNAPSHOT.jar

java -Dspring.profiles.active=dev -Dspring.cloud.nacos.config.namespace=ns_dev -jar example-0.0.1-SNAPSHOT.jar

java -Dspring.profiles.active=test \
     -Dspring.cloud.nacos.config.namespace=ns_test \
     -Dspring.cloud.nacos.config.group=TEST_GROUP \
     -jar example-0.0.1-SNAPSHOT.jar
```

