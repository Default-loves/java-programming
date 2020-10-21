package com.junyi.mqtt.sample.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @time: 2020/10/21 16:32
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Configuration
@ConfigurationProperties("drzk.mqtt")
public class MqttConfigDO {

    private String host;
    private String userName;
    private String password;
    private Integer connectionTimeout;
    private Integer keepAliveInterval;
    private Integer qos;
    private List<String> subscribeTopic;
    private String publishTopic;

    public String getPublishTopic() {
        return publishTopic;
    }

    public void setPublishTopic(String publishTopic) {
        this.publishTopic = publishTopic;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }

    public List<String> getSubscribeTopic() {
        return subscribeTopic;
    }

    public void setSubscribeTopic(List<String> subscribeTopic) {
        this.subscribeTopic = subscribeTopic;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getKeepAliveInterval() {
        return keepAliveInterval;
    }

    public void setKeepAliveInterval(Integer keepAliveInterval) {
        this.keepAliveInterval = keepAliveInterval;
    }

    @Override
    public String toString() {
        return "MqttConfigDO{" +
                "host='" + host + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", connectionTimeout=" + connectionTimeout +
                ", keepAliveInterval=" + keepAliveInterval +
                ", qos=" + qos +
                ", subscribeTopic=" + subscribeTopic +
                '}';
    }
}
