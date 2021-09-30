

### Grafana && Prometheus

Grafana是一款开源的数据可视化和分析工具，不管你的指标信息存储在哪里，你都可以用它来可视化这些数据。同时它还具有告警功能，当指标超出指定范围时会提醒你。

Prometheus是一款时序数据库，可以简单理解为带时间的MySQL数据库。由于Grafana只能将数据转换成可视化图表，并没有存储功能，所以我们需要结合Prometheus这类时序数据库一起使用。



### 安装

- 首先下载Grafana的Docker镜像；

```
docker pull grafana/grafana
```

- 下载完成后运行Grafana；

```
docker run -p 3000:3000 --name grafana \
-d grafana/grafana
```

- 接下来下载Prometheus的Docker镜像；

```
docker pull prom/prometheus
```

- 在`/mydata/prometheus/`目录下创建Prometheus的配置文件`prometheus.yml`：

```
global:
  scrape_interval: 5s
```

- 运行Prometheus，把宿主机中的配置文件`prometheus.yml`挂载到容器中去；

```
docker run -p 9090:9090 --name prometheus \
-v /mydata/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml \
-d prom/prometheus
```

- 至此安装完成，是不是很简单！可以通过如下地址访问Grafana，登录账号密码为`admin:admin`，访问地址：http://192.168.5.78:3000/
- 其实Prometheus也是有可视化界面的，就是有点简陋，访问地址：http://192.168.5.78:9090/



### 监控系统信息

使用`node_explorer`可以暴露Linux系统的指标信息，然后Prometheus就可以通过定时扫描的方式获取并存储指标信息了。

- 下载`node_explorer`的安装包，下载地址：https://prometheus.io/download/#node_exporter
- 将安装包放置到LInux服务器后，解压

```
cd /mydata
tar -zxvf node_exporter-1.1.2.linux-amd64.tar.gz
mv node_exporter-1.1.2.linux-amd64 node_exporter
```

- 进入解压目录，使用如下命令运行`node_explorer`，服务将运行在`9100`端口上；

```
cd node_exporter
./node_exporter >log.file 2>&1 &
```

- 使用`curl`命令访问获取指标信息接口，获取到信息表示运行成功；

```
curl http://localhost:9100/metrics
```

修改Prometheus的配置文件`prometheus.yml`，创建一个任务定时扫描`node_explorer`暴露的指标信息；

```yaml
scrape_configs:
  - job_name: node
    static_configs:
    - targets: ['192.168.5.78:9100']
```

重启Prometheus，通过`加号->Dashboard`来创建仪表盘；

还可以选择去Grafana的仪表盘市场下载一个Dashboard，市场地址：https://grafana.com/grafana/dashboards

选择导入Dashboard并输入ID，最后点击`Load`，选择数据源即可



### 监控Spring应用

监控SpringBoot应用需要依靠`actuator`及`micrometer`，通过暴露`actuator`的端点，Prometheus可以定时获取并存储指标信息。

修改pom.xml文件，添加依赖：

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <!-- 集成micrometer，将监控数据存储到prometheus -->
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-registry-prometheus</artifactId>
    </dependency>
</dependencies>
```

修改应用程序的application.yml文件

```yaml
management:
  endpoints:
    web:
      exposure:
        # 暴露端点`/actuator/prometheus`
        include: 'prometheus'
  metrics:
    tags:
      application: ${spring.application.name}
```

- 修改Prometheus的配置文件`prometheus.yml`，创建一个任务定时扫描`actuator`暴露的指标信息，这里需要注意下，由于SpringBoot应用运行在Docker容器中，需要使用`docker inspect mall-tiny-grafana |grep IPAddress`来获取容器IP地址；

- 同样，我们可以从仪表盘市场导入仪表盘，访问地址：https://grafana.com/grafana/dashboards/14370
- 导入成功后就可以在Grafana中看到SpringBoot实时监控信息了