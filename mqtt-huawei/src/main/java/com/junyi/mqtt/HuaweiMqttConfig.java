package com.junyi.mqtt;

import com.junyi.util.SecurityUtil;
import com.junyi.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
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

import java.io.IOException;
import java.util.*;


/**
 * 华为 IoTDA 端 MQTT 配置
 * @time: 2020/10/21 11:09
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
@Configuration
public class HuaweiMqttConfig {

    @Autowired
    MqttConfigInfo configInfo;


    /**
     * 创建MqttPahoClientFactory，设置MQTT Broker连接属性，如果使用SSL验证，也在这里设置。
     */
    public MqttPahoClientFactory mqttClientFactory() {
        log.info("huawei mqtt configuration: {}", configInfo);
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();

        MqttConnectOptions options = new MqttConnectOptions();

        String ip = null;
        try {
            ip = isSSL(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("【huawei MQTT ip: {}】", ip);

        options.setServerURIs(new String[]{ip});
        options.setCleanSession(false);
        String userName = configInfo.getUserName();
        String pwd = Utils.getPassword(configInfo.getPassword());
        String clientId = Utils.getClientId(configInfo.getUserName());
        log.info("userName: {}, password: {}, clientId: {}", userName, pwd, clientId);
        options.setUserName(userName);
        options.setPassword(pwd.toCharArray());
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(configInfo.getConnectionTimeout());
        options.setKeepAliveInterval(configInfo.getKeepAliveInterval());
        factory.setConnectionOptions(options);
        return factory;
    }

    /** 判断是否开启ssl, 然后返回 MQTT 连接的ip
     * @return*/
    private String isSSL(MqttConnectOptions options) throws IOException {

        String v = "0";
        String host = configInfo.getHost();
        String ip;
        if ("0".equals(v)) {
            ip = "tcp://" + host + ":" + 1883; //mqtt连接
        } else {
            ip = "ssl://" + host + ":" + 8883; //mqtts连接
//            ip = "ssl://" + host + ":" + 28883; //mqtts连接
            ClassPathResource classPathResource = new ClassPathResource(configInfo.getCertificatePath());      // 加载ssl证书文件
            options.setSocketFactory(SecurityUtil.getOptionSocketFactory(classPathResource.getFilename(), classPathResource.getInputStream()));
        }
        return ip;
    }

    @Bean
    public MessageChannel huaweiMqttInputChannel() {
        return new DirectChannel();
    }

    /** 订阅 */
    @Bean
    public MessageProducer huaweiInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                Utils.getClientId(configInfo.getUserName()), mqttClientFactory(),
                subscribeTopic());

        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(configInfo.getQos());
        adapter.setOutputChannel(huaweiMqttInputChannel());
        return adapter;
    }

    private String[] subscribeTopic() {
        ArrayList<String> list = new ArrayList<>();
        list.add(String.format(HuaweiMqttTopic.COMMANDS, configInfo.getUserName()));
        list.add("/apple/add");
        log.info("subscribe huawei topic： {}", Arrays.toString(list.toArray()));
        return list.toArray(new String[0]);
    }

    // ServiceActivator 注解表明当前方法用于处理MQTT消息，inputChannel参数指定了用于接收消息信息的channel
    @Bean
    @ServiceActivator(inputChannel = "huaweiMqttInputChannel")
    public HuaweiMessageHandler huaweiMessageHandler() {
        return new HuaweiMessageHandler();
    }

    @Bean
    public MessageChannel huaweiMqttOutboundChannel() {
        return new DirectChannel();
    }

    /*****
     * 发送消息和消费消息Channel可以使用相同MqttPahoClientFactory
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "huaweiMqttOutboundChannel")
    public MessageHandler huaweiOutbound() {
        // 在这里进行mqttOutboundChannel的相关设置
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler(Utils.getClientId(configInfo.getUserName()), mqttClientFactory());
        //如果设置成true，发送消息时将不会阻塞。
        messageHandler.setAsync(true);
        messageHandler.setDefaultQos(0);
        messageHandler.setDefaultTopic(configInfo.getPublishTopic());
        return messageHandler;
    }
}
