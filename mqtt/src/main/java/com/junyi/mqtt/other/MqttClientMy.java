package com.junyi.mqtt.other;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

/**
 * @time: 2020/10/20 18:16
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
@Component
public class MqttClientMy {
    int qos             = 0;
    String broker       = "tcp://localhost:1883";
    String clientId     = "JavaSample";
    private static MqttClient client = null;



    public void start() throws MqttException {
        client = new MqttClient(broker, clientId, new MemoryPersistence());
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        client.connect(connOpts);
        log.info("Connecting to broker: {}", broker);
    }

    public void send(String topic, String message) {
        try {
            System.out.println("Publishing message: "+ message);
            MqttMessage msg = new MqttMessage(message.getBytes());
            msg.setQos(qos);
            client.publish(topic, msg);

            client.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
}
