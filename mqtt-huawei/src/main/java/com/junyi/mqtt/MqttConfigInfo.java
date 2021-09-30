package com.junyi.mqtt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @time: 2020/10/21 16:32
 * @version: 1.0
 * @author: junyi Xu
 * @description: HLink
 */
@Configuration
@ConfigurationProperties("huawei.mqtt")
public class MqttConfigInfo {

    private String host;
    private String userName;
    private String password;
    private String clientId;
    private Integer connectionTimeout;
    private Integer keepAliveInterval;
    private Integer qos;
    private String certificatePath;     // SSL 证书地址
    private String publishTopic;
    private List<String> subscribeTopic;

    public String getCertificatePath() {
        return certificatePath;
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getHost() {
        return host;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public Integer getKeepAliveInterval() {
        return keepAliveInterval;
    }

    public Integer getQos() {
        return qos;
    }

    public List<String> getSubscribeTopic() {
        return subscribeTopic;
    }

    public String getPublishTopic() {
        return publishTopic;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setKeepAliveInterval(Integer keepAliveInterval) {
        this.keepAliveInterval = keepAliveInterval;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }

    public void setSubscribeTopic(List<String> subscribeTopic) {
        this.subscribeTopic = subscribeTopic;
    }

    public void setPublishTopic(String publishTopic) {
        this.publishTopic = publishTopic;
    }

    @Override
    public String toString() {
        return "MqttConfigHLinkDO{" +
                "host='" + host + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", connectionTimeout=" + connectionTimeout +
                ", keepAliveInterval=" + keepAliveInterval +
                ", qos=" + qos +
                ", publishTopic='" + publishTopic + '\'' +
                ", subscribeTopic=" + subscribeTopic +
                '}';
    }
}
