package com.junyi.mqtt.controller;

import com.junyi.mqtt.sample.MqttConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @time: 2021/9/27 10:24
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@RestController
@RequestMapping("connect")
@Slf4j
public class ConnectController {

    @Autowired
    MqttConfig mqttConfig;

    @GetMapping("stop")
    public void stop() {
        MqttPahoMessageDrivenChannelAdapter adapter = mqttConfig.getAdapter();
        adapter.stop();
    }

    @GetMapping("start")
    public void start() {
        MqttPahoMessageDrivenChannelAdapter adapter = mqttConfig.getAdapter();
        adapter.start();
    }
}
