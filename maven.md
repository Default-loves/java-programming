
项目依赖管理工具

### 依赖关系

- compile：编译时需要用到该jar包(默认)，例如commons-logging
- test：编译Test时需要用到该jar包，例如junit
- runtime：编译时不需要，但运行时需要用到，例如mysql
- provided：编译时需要用到，但运行时由JDK或某个服务器提供，例如servlet-api

### 常用的插件
maven-shade-plugin：打包所有依赖包并生成可执行jar；
cobertura-maven-plugin：生成单元测试覆盖率报告；
findbugs-maven-plugin：对Java源码进行静态分析以找出潜在问题。

### Maven Wrapper
我们安装Maven时，默认情况下，系统所有项目都会使用全局安装的这个Maven版本。但是，对于某些项目来说，它可能必须使用某个特定的Maven版本，这个时候，就可以使用Maven Wrapper，它可以负责给这个特定的项目安装指定版本的Maven，而其他项目不受影响。

给一个项目提供一个独立的，指定版本的Maven给它使用

其项目结构：
my-project
├── .mvn
│   └── wrapper
│       ├── MavenWrapperDownloader.java
│       ├── maven-wrapper.jar
│       └── maven-wrapper.properties
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   └── resources
    └── test
        ├── java
        └── resources



### Snapshot和Release版本

snapshot表示不稳定的版本，Release表示稳定的版本

如果项目A版本号设置为snapshot，那么依赖项目A的项目B在每次编译打包的时候，都会重新去加载项目A的最新版本（即使本地已经存在项目A的安装包），对于频繁更新的项目，需要标记为Snapshot，使依赖项目总能获取到最新的安装包



### Maven构建的生命周期

| 验证 validate | 验证项目 | 验证项目是否正确且所有必须信息是可用的                   |
| ------------- | -------- | -------------------------------------------------------- |
| 编译 compile  | 执行编译 | 源代码编译在此阶段完成                                   |
| 测试 Test     | 测试     | 使用适当的单元测试框架（例如JUnit）运行测试。            |
| 包装 package  | 打包     | 创建JAR/WAR包如在 pom.xml 中定义提及的包                 |
| 检查 verify   | 检查     | 对集成测试的结果进行检查，以保证质量达标                 |
| 安装 install  | 安装     | 安装打包的项目到本地仓库，以供其他项目使用               |
| 部署 deploy   | 部署     | 拷贝最终的工程包到远程仓库中，以共享给其他开发人员和工程 |







### mirror

```xml
<mirrors>
        <mirror>
            <id>alimaven</id>
            <name>aliyun maven</name>
            <url>http://maven.aliyun.com/nexus/content/repositories/central/</url>
<!--            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>-->
            <mirrorOf>central</mirrorOf>
        </mirror>

        <mirror>
            <id>uk</id>
            <mirrorOf>central</mirrorOf>
            <name>Human Readable Name for this Mirror.</name>
            <url>http://uk.maven.org/maven2/</url>
        </mirror>

        <mirror>
            <id>CN</id>
            <name>OSChina Central</name>
            <url>http://maven.oschina.net/content/groups/public/</url>
            <mirrorOf>central</mirrorOf>
        </mirror>

        <mirror>
            <id>nexus</id>
            <name>internal nexus repository</name>
            <!-- <url>http://192.168.1.100:8081/nexus/content/groups/public/</url>-->
            <url>http://repo.maven.apache.org/maven2</url>
            <mirrorOf>central</mirrorOf>
        </mirror>
    </mirrors>
```



### repositories

```xml
<repositories>  
    <repository>  
        <id>alimaven</id>  
        <name>aliyun maven</name>  
        <url>http://maven.aliyun.com/nexus/content/groups/public/</url>  
        <releases>  
            <enabled>true</enabled>  
        </releases>  
        <snapshots>  
            <enabled>false</enabled>  
        </snapshots>  
    </repository>  
</repositories>
```

