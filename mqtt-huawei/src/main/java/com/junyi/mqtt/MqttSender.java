package com.junyi.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @time: 2021/9/9 17:48
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
@Service("mqttSenderHuawei")
public class MqttSender {

    @Resource
    MqttGateway mqttGateway;
    @Resource
    MqttConfigInfo mqttConfigInfo;

    public void send(String topic, String msg) {
        log.info("发送数据，topic：{}， message：{}", topic, msg);
        mqttGateway.sendToMqtt(topic, msg);
    }

    public void sendResponse(String msg) {
    }

    public void sendMessageUp(Object list, String tag, String deviceId) {
    }
}
