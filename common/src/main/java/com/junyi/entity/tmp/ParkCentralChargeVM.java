package com.junyi.entity.tmp;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * @time: 2021/5/29 10:51
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */

public class ParkCentralChargeVM {

    private Integer id;

    private String inID;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Excel(name = "收费时间",width = 20,orderNum = "0",format = "yyyy-MM-dd HH:mm:ss", needMerge = true)
    private Date payChargeTime;						// 收费时间
    @Excel(name = "应收金额", width = 20, orderNum = "1",numFormat="0.0", needMerge = true)
    private Double accountCharge;		//应收金额
    @Excel(name = "实收金额", width = 20, orderNum = "2", needMerge = true)
    private Double payCharge;				//实收金额
    @Excel(name = "减免金额", width = 20, orderNum = "3", needMerge = true)
    private Double disAmount;				//折扣金额

    @ExcelCollection(name = "coupon", orderNum = "4")
    private List<CouponVM> couponVMList;

    public ParkCentralChargeVM() {
    }

    public List<CouponVM> getCouponVMList() {
        return couponVMList;
    }

    public void setCouponVMList(List<CouponVM> couponVMList) {
        this.couponVMList = couponVMList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInID() {
        return inID;
    }

    public void setInID(String inID) {
        this.inID = inID;
    }

    public Date getPayChargeTime() {
        return payChargeTime;
    }

    public void setPayChargeTime(Date payChargeTime) {
        this.payChargeTime = payChargeTime;
    }

    public Double getAccountCharge() {
        return accountCharge;
    }

    public void setAccountCharge(Double accountCharge) {
        this.accountCharge = accountCharge;
    }

    public Double getPayCharge() {
        return payCharge;
    }

    public void setPayCharge(Double payCharge) {
        this.payCharge = payCharge;
    }

    public Double getDisAmount() {
        return disAmount;
    }

    public void setDisAmount(Double disAmount) {
        this.disAmount = disAmount;
    }

}
