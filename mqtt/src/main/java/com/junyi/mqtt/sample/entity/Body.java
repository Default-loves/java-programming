package com.junyi.mqtt.sample.entity;

import java.io.Serializable;

/**
 * @time: 2020/11/18 9:05
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class Body implements Serializable {
    private static final long serialVersionUID = 2987038369509319247L;
    private String uId;
    private String parkingNo;//车场编号
    private String equipmentID;//设备编号
    private String conIp;//控制器IP
    private String recordNo;//流水号
    private String eventType;//事件类型
    private String eventTime;//事件时间
    private String carNo;  //车牌号
    private String carType;  //车牌颜色类型0蓝色1黄色2白色3黑色
    private String backCarNo;  //后车牌
    private String mediumType;  //介质类型 0-无，1-IC卡，2-IC转ID卡，3-ID卡，4-ETC，5-CPU，6-纸票，7-身份证UID
    private String mediumData;  //附加介质数据 十六进制，右对齐
    private String frontPic;  //前车牌图片路径
    private String smallFrontPic;  //前车牌小图片路径
    private String backPic;  //后车牌图片路径
    private String smallBackPic;  //后车牌小图片路径
    private String carColorType; //车牌颜色
    private String cameraTriggerType="1";//相机触发类型

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getParkingNo() {
        return parkingNo;
    }

    public void setParkingNo(String parkingNo) {
        this.parkingNo = parkingNo;
    }

    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getConIp() {
        return conIp;
    }

    public void setConIp(String conIp) {
        this.conIp = conIp;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getBackCarNo() {
        return backCarNo;
    }

    public void setBackCarNo(String backCarNo) {
        this.backCarNo = backCarNo;
    }

    public String getMediumType() {
        return mediumType;
    }

    public void setMediumType(String mediumType) {
        this.mediumType = mediumType;
    }

    public String getMediumData() {
        return mediumData;
    }

    public void setMediumData(String mediumData) {
        this.mediumData = mediumData;
    }

    public String getFrontPic() {
        return frontPic;
    }

    public void setFrontPic(String frontPic) {
        this.frontPic = frontPic;
    }

    public String getSmallFrontPic() {
        return smallFrontPic;
    }

    public void setSmallFrontPic(String smallFrontPic) {
        this.smallFrontPic = smallFrontPic;
    }

    public String getBackPic() {
        return backPic;
    }

    public void setBackPic(String backPic) {
        this.backPic = backPic;
    }

    public String getSmallBackPic() {
        return smallBackPic;
    }

    public void setSmallBackPic(String smallBackPic) {
        this.smallBackPic = smallBackPic;
    }

    public String getCarColorType() {
        return carColorType;
    }

    public void setCarColorType(String carColorType) {
        this.carColorType = carColorType;
    }

    public String getCameraTriggerType() {
        return cameraTriggerType;
    }

    public void setCameraTriggerType(String cameraTriggerType) {
        this.cameraTriggerType = cameraTriggerType;
    }

    @Override
    public String toString() {
        return "Body{" +
                "uId='" + uId + '\'' +
                ", parkingNo='" + parkingNo + '\'' +
                ", equipmentID='" + equipmentID + '\'' +
                ", conIp='" + conIp + '\'' +
                ", recordNo='" + recordNo + '\'' +
                ", eventType='" + eventType + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", carNo='" + carNo + '\'' +
                ", carType='" + carType + '\'' +
                ", backCarNo='" + backCarNo + '\'' +
                ", mediumType='" + mediumType + '\'' +
                ", mediumData='" + mediumData + '\'' +
                ", frontPic='" + frontPic + '\'' +
                ", smallFrontPic='" + smallFrontPic + '\'' +
                ", backPic='" + backPic + '\'' +
                ", smallBackPic='" + smallBackPic + '\'' +
                ", carColorType='" + carColorType + '\'' +
                ", cameraTriggerType='" + cameraTriggerType + '\'' +
                '}';
    }
}
