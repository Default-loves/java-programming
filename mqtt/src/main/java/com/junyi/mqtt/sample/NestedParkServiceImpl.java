package com.junyi.mqtt.sample;

import com.alibaba.fastjson.JSON;
import com.junyi.mqtt.sample.entity.Body;
import com.junyi.mqtt.sample.entity.Request;
import com.junyi.mqtt.util.NormalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @time: 2020/11/18 9:00
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Service
@Slf4j
public class NestedParkServiceImpl {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static String TOPIC = "%s/publish/data";


    @Autowired
    private MqttGateway mqttGateway;


    public String inEvent(Request request) {
        String dateTime = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        Body body = request.getBody();
        body.setEventTime(dateTime);
        printf(body);
        String topic = String.format(TOPIC, body.getParkingNo());
        String msg = JSON.toJSONString(request);
        mqttGateway.sendToMqtt(topic, msg);

        return msg;
    }


    public String outEvent(Request request) {
        String dateTime = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        Body body = request.getBody();
        body.setEventTime(dateTime);
        printf(body);
        String topic = String.format(TOPIC, body.getParkingNo());
        String msg = JSON.toJSONString(request);
        mqttGateway.sendToMqtt(topic, msg);
        return msg;
    }

    private void printf(Body body) {
        String eventType = NormalUtil.getEvenType(body.getEventType());
        String loc = NormalUtil.getInOutType(body.getEquipmentID());
        log.info("时间: {}, 类型: {}, 位置: {}, 车牌: {}\n", body.getEventTime(), eventType, loc, body.getCarNo());
    }
}
