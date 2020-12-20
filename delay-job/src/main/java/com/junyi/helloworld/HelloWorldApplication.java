package com.junyi.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

/**
 * 延时任务，指的是在未来的特定时间才进行触发处理，比如下单后的缴费倒计时，资源拥有者过期释放资源等
 * 延时任务的4种实现方式：
 * 1. 使用 concurrent 包下面的 DelayQueue
 * 2. 使用 Netty包的 HashedWheelTimerMy
 * 3. 使用 Redis 的 ZSet
 * 4. 使用 RabbitMQ，对于消息设置 TTL，当消息过期的时候发送到特定的队列中由消费者消费过期消息
 */

public class HelloWorldApplication {
	public static void main(String[] args) {

	}

}
