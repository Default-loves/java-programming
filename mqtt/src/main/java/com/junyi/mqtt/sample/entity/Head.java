package com.junyi.mqtt.sample.entity;

import java.io.Serializable;

/**
 * @time: 2020/11/18 9:05
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class Head implements Serializable {
    private static final long serialVersionUID = -327630079526817594L;
    //答应主题
    private String replyTopic;
    //版本号
    private String version;
    //具体方法
    private String method;
    private String parkingNo;
    private int status = 1;
    private String message = "成功";

    public String getReplyTopic() {
        return replyTopic;
    }

    public void setReplyTopic(String replyTopic) {
        this.replyTopic = replyTopic;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParkingNo() {
        return parkingNo;
    }

    public void setParkingNo(String parkingNo) {
        this.parkingNo = parkingNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Head{" +
                "replyTopic='" + replyTopic + '\'' +
                ", version='" + version + '\'' +
                ", method='" + method + '\'' +
                ", parkingNo='" + parkingNo + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
