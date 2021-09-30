package com.junyi.mqtt;

/**
 * mqtt 主题
 * @time: 2021/9/9 15:07
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class HuaweiMqttTopic {

    /** 下发命令 */
    public static final String COMMANDS = "$oc/devices/%s/sys/commands/#";    // 中间为 deviceId

    // 第一个参数为 deviceId，也即设备的 MAC 地址
    // 第二个为请求id
    /** 命令响应 */
    public static final String COMMANDS_RESPONSE =  "$oc/devices/%s/sys/commands/response/request_id=%s";

    /** 属性上报 */
    public static final String PROPERTIES_REPORT =  "$oc/devices/%s/sys/properties/report";

    /** 消息上报 */
    public static final String MESSAGE_UP =  "$oc/devices/%s/sys/messages/up";


}
