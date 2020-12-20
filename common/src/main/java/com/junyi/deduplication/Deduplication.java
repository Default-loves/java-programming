package com.junyi.deduplication;

import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

/**
 * @time: 2020/12/3 11:05
 * @version: 1.0
 * @author: junyi Xu
 * @description: 对于服务端接收的请求进行去重
 * 1. 如果客户端发送的消息有唯一编号， 那么可以使用唯一编号作为标识
 * 2. 如果没有， 那么需要根据请求构建唯一编号：用户ID:接口名:请求参数的MD5
 */
public class Deduplication {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /** 有唯一编号 */
    public void createUnique() {
        String KEY = "REQ12343456788";//请求唯一编号
        process(KEY);
    }

    /** 没有唯一编号，构造唯一编号 */
    public void createUnique2() {
        String userId= "12345678";//用户
        String method = "pay";//接口名
        String req = "{\n" +
                "\"requestTime\" :\"20190101120001\",\n" +
                "\"requestValue\" :\"1000\",\n" +
                "\"requestKey\" :\"key\"\n" +
                "}";
        String dedupMD5 = new ReqDuplicateHelper().dedupParamMD5(req,"requestTime");//计算请求参数摘要，其中剔除里面请求时间的干扰
        String KEY = "dedup:U=" + userId + "M=" + method + "P=" + dedupMD5;
        process(KEY);
    }

    private void process(String key) {
        long expireTime =  1000;// 1000毫秒过期，1000ms内的重复请求会认为重复
        long expireAt = System.currentTimeMillis() + expireTime;
        String val = "expireAt@" + expireAt;

        //redis key还存在的话要就认为请求是重复的
        Boolean firstSet = stringRedisTemplate.execute((RedisCallback<Boolean>) connection ->
                connection.set(key.getBytes(), val.getBytes(), Expiration.milliseconds(expireTime), RedisStringCommands.SetOption.SET_IF_ABSENT));

        final boolean isConsiderDup;
        if (firstSet != null && firstSet) {     // 第一次访问
            isConsiderDup = false;
        } else {// redis值已存在，认为是重复了
            isConsiderDup = true;
        }
    }



}
