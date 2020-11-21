package com.junyi.mqtt.sample;

import com.junyi.mqtt.sample.entity.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @time: 2020/10/21 11:13
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@RestController
@RequestMapping("nested-park")
@Slf4j
public class MqttController {

    public static final String TOPIC = "X51810900005/publish/data";

    @Autowired
    private MqttGateway mqttGateway;
    @Autowired
    NestedParkServiceImpl service;

    @GetMapping("in")
    public String in(@RequestBody Request request) {
        String s = service.inEvent(request);
        mqttGateway.sendToMqtt(TOPIC, s);
        return "send success";
    }

    @GetMapping("out")
    public String out(@RequestBody Request request) {
        String s = service.outEvent(request);
        mqttGateway.sendToMqtt(TOPIC, s);
        return "send success";
    }
}