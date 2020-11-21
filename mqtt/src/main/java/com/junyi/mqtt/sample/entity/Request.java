package com.junyi.mqtt.sample.entity;

import java.io.Serializable;

/**
 * @time: 2020/11/18 9:06
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class Request implements Serializable {


    private static final long serialVersionUID = -1428162954124106712L;
    private Head head;
    private Body body;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Request{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }
}
