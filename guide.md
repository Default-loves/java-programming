### 编程中需要注意的规范

- 使用 BigDecimal 表示和计算浮点数，且务必使用字符串的构造方法来初始化

- LocalDateTime 不带有时区属性，所以命名为本地时区的日期时间；而ZonedDateTime=LocalDateTime+ZoneId，具有时区属性。

  

1. 超时时间是否合理
2. 考虑到网络是不稳定的，进行重试是有必要的，但是要保证幂等性，避免重复处理
3. 在并发数很大的情况下，确保框架的并发数是否合理，避免HTTP调用成为瓶颈



### 处理异常的一些实践

1. 不建议在框架层面进行异常的自动、统一处理，尤其不要随意捕获异常。但，框架可以做兜底工作。如果异常上升到最上层逻辑还是无法处理的话，可以以统一的方式进行异常转换，比如通过 @RestControllerAdvice + @ExceptionHandler，来捕获这些“未处理”异常
2. 捕获了异常后不要直接生吞，不管是多么不重要的异常，都应该留下哪怕一个日志信息
3. 在捕获异常后，不要丢弃异常的原始信息
4. 避免抛出异常时不指定任何消息，比如`throw new RuntimeException();`





### Code Review实践:

1. 代码需要经过另外一个人(Reviewer)的审核后才能添加进Github
2. Coder在本地IDE需要使用CheckStype等插件, 强制规范代码的格式问题, 然后才能提交Reviewer
3. 对于复杂, 核心的代码, 由团队集体进行Review
4. 连坐机制: 代码的质量由Coder和Reviewer共同负责
5. 小步前进, 每次Review 200~400行的代码
6. 指定Review CheckList, 指明Review中需要关注的要点
7. 极致形式-结对编程. 虽然代码质量能够显著提高, 但是对于Coder来说是很TM累的



### 一个Spring Boot项目的基础设施

分页处理

统一的消息回复

多环境配置

日志文件配置Logback

常用工具封装 Hutool

Entity到Vo的转换

MyBatis Code Generate

全局异常拦截

自定义异常

Swagger

