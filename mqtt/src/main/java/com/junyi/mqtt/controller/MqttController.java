package com.junyi.mqtt.controller;

import com.junyi.mqtt.sample.NestedParkServiceImpl;
import com.junyi.mqtt.sample.entity.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.web.bind.annotation.*;

import static com.junyi.mqtt.sample.NestedParkServiceImpl.TOPIC;

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


    @Autowired
    NestedParkServiceImpl service;


    @GetMapping("in")
    public String in(@RequestBody Request request) {
        String s = service.inEvent(request);
        return "send success";
    }

    @GetMapping("out")
    public String out(@RequestBody Request request) {
        String s = service.outEvent(request);
        return "send success";
    }



}