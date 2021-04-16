package com.junyi.entity;

/**
 * @time: 2021/3/30 11:16
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */

public class Message {
    private String id;
    private String message;

    public Message() {
    }

    public Message(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
