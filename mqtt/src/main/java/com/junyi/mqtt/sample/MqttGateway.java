package com.junyi.mqtt.sample;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @time: 2020/10/21 11:11
 * @version: 1.0
 * @author: junyi Xu
 * @description: send data to MQTT
 */
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {

    void sendToMqtt(String payload);

    // 指定topic进行消息发送
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, String payload);

    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);
}
