package com.junyi.mqtt.sample;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;

import java.util.Objects;

/**
 * @time: 2020/10/27 15:20
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class DefaultMessageHandler implements MessageHandler {
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        log.info("receive message: {}", message);
        String payload = message.getPayload().toString();
        String topic = Objects.requireNonNull(message.getHeaders().get("mqtt_receivedTopic")).toString();
        // 根据topic分别进行消息处理。
        if ("book/update".equals(topic)) {
            log.info("topic:{}, message:{}", topic, payload);
        } else {
            log.info("topic:{}, message:{}", topic, payload);
        }
    }
}
