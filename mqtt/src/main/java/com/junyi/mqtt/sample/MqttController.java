package com.junyi.mqtt.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @time: 2020/10/21 11:13
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@RestController
@RequestMapping("book")
@Slf4j
public class MqttController {

    @Autowired
    private MqttGateway mqttGateway;

    @GetMapping("get")
    public String send(@RequestParam String topic, @RequestParam String message) {
        mqttGateway.sendToMqtt(topic, message);
        log.info("send message: {}", message);
        return "send message : " + message;
    }
}