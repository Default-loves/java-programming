package com.junyi.mqtt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * @time: 2020/10/27 15:20
 * @version: 1.0
 * @author: junyi Xu
 * @description: 接收本地服务的数据
 */
@Slf4j
public class HuaweiMessageHandler implements MessageHandler {



    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        log.info("接收到 huawei 消息：{}", message.toString());

        try {
            String topic = message.getHeaders().get(MqttHeaders.TOPIC).toString();
            JSONObject body = JSONObject.parseObject(message.getPayload().toString());

            String serviceId = body.getString("service_id");
            String commandName = body.getString("command_name");
            String deviceId = body.getString("object_device_id");
            String paras = body.getString("paras");

            // 获取 reque
        } catch (Exception e) {
            log.error("异常：", e);
        }
    }
}
