package com.junyi;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.Random;

/**
 * 分布式锁工具类
 * @time: 2021/4/15 9:29
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class RedisLockUtil {

    // 分布式锁使用的key
    private final String LOCK_KEY = "redis_lock";

    // key的持有时间，5ms
    private long EXPIRE_TIME = 5;

    // 等待超时时间，1s
    private long TIME_OUT = 2000;

    // Redis命令参数，相当于nx和px的命令合集
    private SetParams params = SetParams.setParams().nx().px(EXPIRE_TIME);

    // Redis连接池，连的是本地的Redis客户端
    JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);

    // 删除key的lua脚本
    private final String LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";

    /**
     * 加锁
     * @param id 线程的id，或者其他可识别当前线程且不重复的字段
     * @return
     */
    public boolean lock(String id) {
        Long start = System.currentTimeMillis();
        Jedis jedis = jedisPool.getResource();
        try {
            for (;;) {
                // SET命令返回OK ，则证明获取锁成功
                String lock = jedis.set(LOCK_KEY, id, params);
                if ("OK".equals(lock)) {
                    return true;
                }
                // 否则循环等待，在TIME_OUT时间内仍未获取到锁，则获取失败
                long l = System.currentTimeMillis() - start;
                if (l >= TIME_OUT) {
                    log.info("线程：{} 获取不到锁，等待达到最大等待时间", id);
                    return false;
                }
                try {
                    // 休眠一会，不然反复执行循环会一直失败
                    Thread.sleep(random(200));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            jedis.close();
        }
    }
    private Integer random(Integer k) {
        return new Random().nextInt(k);
    }

    /**
     * 解锁
     *
     * @param id
     *            线程的id，或者其他可识别当前线程且不重复的字段
     * @return
     */
    public boolean unlock(String id) {
        Jedis jedis = jedisPool.getResource();
        try {
            String result = jedis.eval(LUA_SCRIPT, Collections.singletonList(LOCK_KEY), Collections.singletonList(id)).toString();
            return "1".equals(result);
        } finally {
            jedis.close();
        }
    }
}
