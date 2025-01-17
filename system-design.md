系统设计



首先不要急着设计解决方案，而是要确定功能性需求和非功能性需求。

对于功能性需求，将服务进行拆分，切分为多个微服务

非功能性需求要考虑真实的使用场景情况，比如QPS，峰值并发量等，确定解决方案

进行抽象设计，将需要使用到的组件画出来，并且连接组件之间的关系

针对各个组件，进行优化，一般要考虑高可用、高性能、高扩展等

1. **高性能架构设计**：熟悉系统常见性能优化手段比如引入 **读写分离**、**缓存**、负载均衡、**异步** 等等。
2. **高可用架构设计** ：CAP 理论和 BASE 理论、通过集群来提高系统整体稳定性、超时和重试机制、应对接口级故障：**降级**、**熔断**、**限流**、排队。
3. **高扩展架构设计** ：说白了就是懂得如何拆分系统。你按照不同的思路来拆分软件系统，就会得到不同的架构。

当然了，性能优化优先度：

SQL 优化，JVM、DB，Tomcat 参数调优 > 硬件性能优化（内存升级、CPU 核心数增加、机械硬盘—>固态硬盘等等）> 业务逻辑优化/缓存 > 读写分离、集群等 > 分库分表



### 性能相关的指标

- 相应时间RT：响应时间 RT(Response time)就是用户发出请求到用户收到系统处理结果所需要的时间。
- 并发数：并发数可以简单理解为系统能够同时供多少人访问使用也就是说系统同时能处理的请求数量。
- QPS（Query per second）：服务器每秒可以执行的请求数量
- TPS（Transaction per second）：服务器每秒可以执行的事务数量。相比较于QPS，比如用户访问一个页面，但是页面向服务器发送了3个请求，那么这儿TPS为1，QPS为3。
- PV(Page View)：页面浏览的次数
- UV(Unique Visitor)：独立访客数，时间内访问站点的人数，以cookie或者ip为依据
- DAU（Daily Active User）：日活跃用户数量
- MAU（Monthly Active User）：月活跃用户数量

#### 计算的例子

DAU为1500W，用户平均每日使用时长1小时，RT为0.5s，那么

平均并发量：`1500W * 1H / 24H=62.5W `

真实并发量，即认为24小时中8小时是睡觉时间，其请求量很小需要去除掉：`1500W * 1H / (24H-8H) =93.75W`

峰值并发量：平均并发量*6=375W

QPS：真实并发量 / RT = 187.5W/s



### 常用组件的QPS

- Nginx：通常来说，Nginx不会是性能瓶颈，其QPS是很高的，能够达到30W+。
- Redis：QPS能够达到8W+。
- MySQL：QPS大概是4K。
- 



