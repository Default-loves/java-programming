package com.junyi.mqtt.sample;

import com.junyi.mqtt.sample.entity.MqttConfigDO;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * @time: 2020/10/21 11:09
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Configuration
@Slf4j
public class MqttConfig  {

    @Autowired
    MqttConfigDO mqttConfigDO;


    /*****
     * 创建MqttPahoClientFactory，设置MQTT Broker连接属性，如果使用SSL验证，也在这里设置。
     * @return
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {

        log.info("mqtt configuration: {}", mqttConfigDO);
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();

        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setServerURIs(new String[]{mqttConfigDO.getHost()});
        options.setUserName(mqttConfigDO.getUserName());
        options.setPassword(mqttConfigDO.getPassword().toCharArray());
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(mqttConfigDO.getConnectionTimeout());
        options.setKeepAliveInterval(mqttConfigDO.getKeepAliveInterval());
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("consumerClient",
                mqttClientFactory(), mqttConfigDO.getSubscribeTopic().toArray(new String[0]));
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(mqttConfigDO.getQos());
        adapter.setOutputChannel(mqttInputChannel());

        return adapter;
    }

    //ServiceActivator注解表明当前方法用于处理MQTT消息，inputChannel参数指定了用于接收消息信息的channel
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public DefaultMessageHandler handler() {
        return new DefaultMessageHandler();
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    /*****
     * 发送消息和消费消息Channel可以使用相同MqttPahoClientFactory
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler outbound() {
        // 在这里进行mqttOutboundChannel的相关设置
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler("publishClient", mqttClientFactory());
        //如果设置成true，发送消息时将不会阻塞。
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(mqttConfigDO.getPublishTopic());
        return messageHandler;
    }
}
