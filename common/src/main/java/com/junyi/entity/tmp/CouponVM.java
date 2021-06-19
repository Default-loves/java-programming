package com.junyi.entity.tmp;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @time: 2021/5/29 10:54
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class CouponVM {

    private Integer id;
    private Integer chargeId;

    @Excel(name = "打折", orderNum = "23")
    private String discounted;      // 是否打折     “是”，“否”
    @Excel(name = "减免方案", width = 20, orderNum = "25")
    private String discountWay;     // 减免方案
    @Excel(name = "减免状态", width = 20, orderNum = "24")
    private String discountStatus;  // 减免状态
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Excel(name = "减免时间", width = 20, format = "yyyy-MM-dd HH:mm:ss", orderNum = "22")
    private Date discountTime;  // 减免时间
    @Excel(name = "门店名称", orderNum = "21", width = 20)
    private String equipmentName;   // 门店名称

    public CouponVM() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChargeId() {
        return chargeId;
    }

    public void setChargeId(Integer chargeId) {
        this.chargeId = chargeId;
    }

    public String getDiscounted() {
        return discounted;
    }

    public void setDiscounted(String discounted) {
        this.discounted = discounted;
    }

    public String getDiscountWay() {
        return discountWay;
    }

    public void setDiscountWay(String discountWay) {
        this.discountWay = discountWay;
    }

    public String getDiscountStatus() {
        return discountStatus;
    }

    public void setDiscountStatus(String discountStatus) {
        this.discountStatus = discountStatus;
    }

    public Date getDiscountTime() {
        return discountTime;
    }

    public void setDiscountTime(Date discountTime) {
        this.discountTime = discountTime;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
}
