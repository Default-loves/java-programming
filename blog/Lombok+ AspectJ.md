Lombok和AspectJ都是在字节码层面对代码进行增强，一起使用会存在一些问题，下面介绍同时使用Lombok和AspectJ的正确姿势：



引入依赖`spring-aspects`

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aspects</artifactId>
</dependency>
```



添加plugin，包括`lombok-maven-plugin`和`aspectj-maven-plugin`。

将代码中的 Lombok 注解先编译为代码，这样 AspectJ 编译不会有问题

```xml
<plugin>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok-maven-plugin</artifactId>
    <version>1.18.0.0</version>
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>delombok</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <addOutputDirectory>false</addOutputDirectory>
        <sourceDirectory>src/main/java</sourceDirectory>
    </configuration>
</plugin>
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>aspectj-maven-plugin</artifactId>
    <version>1.10</version>
    <configuration>
        <complianceLevel>1.8</complianceLevel>
        <source>1.8</source>
        <aspectLibraries>
            <aspectLibrary>
                <groupId>org.springframework</groupId>
                <artifactId>spring-aspects</artifactId>
            </aspectLibrary>
        </aspectLibraries>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>compile</goal>
                <goal>test-compile</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```



设置 @EnableTransactionManagement 注解，开启事务管理走 AspectJ 模式：

```java
@SpringBootApplication
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class CommonMistakesApplication {
```

