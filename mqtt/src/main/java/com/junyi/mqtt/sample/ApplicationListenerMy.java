package com.junyi.mqtt.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.integration.mqtt.event.MqttIntegrationEvent;
import org.springframework.integration.mqtt.event.MqttMessageDeliveredEvent;

/**
 * @time: 2020/10/27 16:15
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class ApplicationListenerMy implements ApplicationListener<MqttIntegrationEvent> {

    @Override
    public void onApplicationEvent(MqttIntegrationEvent event) {
        log.info("onApplicationEvent: {}", event.toString());
    }
}
