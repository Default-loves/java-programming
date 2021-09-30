

### Mqtt接收不到消息

**问题描述**

Mqtt订阅的主题没有问题，配置类也有打印日志，但是MessageHandler消息接收类总是收不到消息

**原因**

配置类MqttConfig和其他的类重名了，导致配置类被覆盖了

**解决**

将配置类重名即可。这也提示我们被Spring管理的类，包括@Component、@Configuration、@Service等类需要注意类的名字

