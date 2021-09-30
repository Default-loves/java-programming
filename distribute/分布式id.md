

### 分布式id的要求

- **全局唯一** ：ID 的全局唯一性肯定是首先要满足的！
- **高性能** ：分布式 ID 的生成速度要快，对本地资源消耗要小。
- **高可用** ：生成分布式 ID 的服务要保证可用性无限接近于 100%。
- **方便易用** ：拿来即用，使用方便，快速接入！

除了这些之外，一个比较好的分布式 ID 还应保证：

- **安全** ：ID 中不包含敏感信息。
- **有序递增** ：如果要把 ID 存放在数据库的话，ID 的有序性可以提升数据库写入速度。并且，很多时候 ，我们还很有可能会直接通过 ID 来进行排序。
- **有具体的业务含义** ：生成的 ID 如果能有具体的业务含义，可以让定位问题以及开发更透明化（通过 ID 就能确定是哪个业务）。
- **独立部署** ：也就是分布式系统单独有一个发号器服务，专门用来生成分布式 ID。这样就生成 ID 的服务可以和业务相关的服务解耦。不过，这样同样带来了网络调用消耗增加的问题。总的来说，如果需要用到分布式 ID 的场景比较多的话，独立部署的发号器服务还是很有必要的。





### mysql号段模式

```mysql
CREATE TABLE `sequence_id_generator` (
  `id` int(10) NOT NULL,
  `current_max_id` bigint(20) NOT NULL COMMENT '当前最大id',
  `step` int(10) NOT NULL COMMENT '号段的长度',
  `version` int(20) NOT NULL COMMENT '版本号',
  `biz_type`    int(20) NOT NULL COMMENT '业务类型',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 首先插入一条记录
INSERT INTO `sequence_id_generator` (`id`, `current_max_id`, `step`, `version`, `biz_type`)
VALUES(1, 0, 100, 0, 101);

# 客户端申请一批id，先要获取最新的信息
SELECT `current_max_id`, `step`,`version` FROM `sequence_id_generator` where `biz_type` = 101

# 尝试更新mysql中的id最大值
UPDATE sequence_id_generator SET current_max_id = 0+100, version=version+1 WHERE version = 0  AND `biz_type` = 101
# 查询最新的结果
SELECT `current_max_id`, `step`,`version` FROM `sequence_id_generator` where `biz_type` = 101
```

**Tinyid**是滴滴开源的一款基于数据库号段模式的唯一 ID 生成器，在号段模式下增加了双号段模式、增加多DB支持等

### Redis

通过Redis的`incr`实现原子序列递增

```shell
127.0.0.1:6379> set sequence_id_biz_type 1
OK
127.0.0.1:6379> incr sequence_id_biz_type
(integer) 2
127.0.0.1:6379> get sequence_id_biz_type
"2"
```



### SnowFlake（雪花算法）

snowflake算法生成的id是64bit的二进制数字，每个部位表示了不同的含义：

- 第一部分（1bit）：标识位
- 第二部分（41bit）：时间戳，单位是毫秒
- 第三部分（10bit）：机器号
- 第四部分（12bit）：自增id，允许在同一毫秒单个机器同时生成4096个不同的id

很多开源算法对snowflake进行了改进，比如美团 的 Leaf、百度的 UidGenerator（已不维护），建议可以直接使用美团的Leaf开源算法

















