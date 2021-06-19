package com.junyi.entity.tmp;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zhangbin
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: 出入明细报表
 * @date 2018/11/2 14:50
 */
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
@ExcelTarget("vwEntryExitVo")
public class VwEntryExitVo extends BaseVo implements Serializable {
    private static final long serialVersionUID = -546117084599386601L;

    private String guid;                            //出场的guid
    private String inID;                            //入场id
    private Integer inMachNo;                //入场机号
    private Integer outMachNo;                //出场机号
    private String inCarNo;                        //入场主车牌号
    private String backInCarNo;                //入场副车牌号
    private String outCarNo;                    //出场主车牌号
    private String backOutCarNo;            //出场副车牌号
    private Integer carNoType;                //车牌类型
    private Integer cardType;                    //卡类型

    private Integer freeType;                    //免费类型
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date inTime;                            //入场时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date outTime;                        //出场时间

    private String inPic;                            //入场主车牌图片
    private String backInPic;                    //入场副车牌图片
    private String outPic;                        //出场主车牌图片
    private String backOutPic;                //出场副车牌图片

    private String inUserName;                //入场操作员
    private String outUserName;            //出场操作员

    private Double balanceMoney;            //帐户金额
    private String memo;                        //备注
    private Integer isLoad;                        //上传状态：0-未上传；1-正在上传；2-上传成功
    //    =true,numFormat="0.0")
    private Double accountCharge;		//应收金额
    //    =true)
    private Double payCharge;				//实收金额
    //    =true)
    private Double disAmount;				//折扣金额
    private String auditUse;                    //审核人
    //true)
    private Double freeAmount;                //免费金额
    private String inChannelName;            //入场通道名
    private String outChannelName;        //出场通道名
    private String cardTypeName;            //卡类型自定义名称
    private Integer outWay;                    //出场类型
    private String outWayName;            //出场类型名称
    private String freeName;
    //    2"},orderNum = "11")
    private Integer auditStatus;                //稽查状态：0.未确认 1.申请正常 2.申请异常
    //    uditUse;                    //审核人
    private String orderNum;                    //订单号
    private Integer selDateType;                //查询日期类型：0-不查询日期范围；1-查询入场日期范围；2-查询出场日期范围


    private String parkName;    // 车场名称

    private Integer parkType;   //车场类型：1标准车场，2小车场，3大车场'

    private String npid;    //嵌套车场中，唯一标识了一次行程中的多条记录

    private Integer countInOnce;    // 嵌套车场中，统计一个车辆一次出行在整个嵌套车场的出入记录数量

    private String sTime;                            //开始时间
    private String eTime;                        //结束时间
    private Integer index;              //序号
    private String moreFreeType;        //批量多免费类型
    private String moreOutWay;          //批量多出场方式

    private String payUserName;         //出场收费员

    @Excel(name = "车牌号码", width = 20, orderNum = "0", needMerge = true)
    private String carNo;               //车牌号码
    private String credentialsPic;          //证件图片路径


    private String payTypeName;     // 支付方式的名称
    private Integer payType;            //支付方式


    private String parkTime;    // 停车时长

    private Integer chargeId;   // 缴费记录表的主键

    @ExcelCollection(name = "缴费信息", orderNum = "1")
    private List<ParkCentralChargeVM> parkCentralChargeVMList;


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

    public List<ParkCentralChargeVM> getParkCentralChargeVMList() {
        return parkCentralChargeVMList;
    }

    public void setParkCentralChargeVMList(List<ParkCentralChargeVM> parkCentralChargeVMList) {
        this.parkCentralChargeVMList = parkCentralChargeVMList;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getInID() {
        return inID;
    }

    public void setInID(String inID) {
        this.inID = inID;
    }

    public Integer getInMachNo() {
        return inMachNo;
    }

    public void setInMachNo(Integer inMachNo) {
        this.inMachNo = inMachNo;
    }

    public Integer getOutMachNo() {
        return outMachNo;
    }

    public void setOutMachNo(Integer outMachNo) {
        this.outMachNo = outMachNo;
    }

    public String getInCarNo() {
        return inCarNo;
    }

    public void setInCarNo(String inCarNo) {
        this.inCarNo = inCarNo;
    }

    public String getBackInCarNo() {
        return backInCarNo;
    }

    public void setBackInCarNo(String backInCarNo) {
        this.backInCarNo = backInCarNo;
    }

    public String getOutCarNo() {
        return outCarNo;
    }

    public void setOutCarNo(String outCarNo) {
        this.outCarNo = outCarNo;
    }

    public String getBackOutCarNo() {
        return backOutCarNo;
    }

    public void setBackOutCarNo(String backOutCarNo) {
        this.backOutCarNo = backOutCarNo;
    }

    public Integer getCarNoType() {
        return carNoType;
    }

    public void setCarNoType(Integer carNoType) {
        this.carNoType = carNoType;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getFreeType() {
        return freeType;
    }

    public void setFreeType(Integer freeType) {
        this.freeType = freeType;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String getInPic() {
        return inPic;
    }

    public void setInPic(String inPic) {
        this.inPic = inPic;
    }

    public String getBackInPic() {
        return backInPic;
    }

    public void setBackInPic(String backInPic) {
        this.backInPic = backInPic;
    }

    public String getOutPic() {
        return outPic;
    }

    public void setOutPic(String outPic) {
        this.outPic = outPic;
    }

    public String getBackOutPic() {
        return backOutPic;
    }

    public void setBackOutPic(String backOutPic) {
        this.backOutPic = backOutPic;
    }

    public String getInUserName() {
        return inUserName;
    }

    public void setInUserName(String inUserName) {
        this.inUserName = inUserName;
    }

    public String getOutUserName() {
        return outUserName;
    }

    public void setOutUserName(String outUserName) {
        this.outUserName = outUserName;
    }

    public Double getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(Double balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getIsLoad() {
        return isLoad;
    }

    public void setIsLoad(Integer isLoad) {
        this.isLoad = isLoad;
    }

    public Double getFreeAmount() {
        return freeAmount;
    }

    public void setFreeAmount(Double freeAmount) {
        this.freeAmount = freeAmount;
    }

    public String getInChannelName() {
        return inChannelName;
    }

    public void setInChannelName(String inChannelName) {
        this.inChannelName = inChannelName;
    }

    public String getOutChannelName() {
        return outChannelName;
    }

    public void setOutChannelName(String outChannelName) {
        this.outChannelName = outChannelName;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public Integer getOutWay() {
        return outWay;
    }

    public void setOutWay(Integer outWay) {
        this.outWay = outWay;
    }

    public String getOutWayName() {
        return outWayName;
    }

    public void setOutWayName(String outWayName) {
        this.outWayName = outWayName;
    }

    public String getFreeName() {
        return freeName;
    }

    public void setFreeName(String freeName) {
        this.freeName = freeName;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditUse() {
        return auditUse;
    }

    public void setAuditUse(String auditUse) {
        this.auditUse = auditUse;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getSelDateType() {
        return selDateType;
    }

    public void setSelDateType(Integer selDateType) {
        this.selDateType = selDateType;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public Integer getParkType() {
        return parkType;
    }

    public void setParkType(Integer parkType) {
        this.parkType = parkType;
    }

    public String getNpid() {
        return npid;
    }

    public void setNpid(String npid) {
        this.npid = npid;
    }

    public Integer getCountInOnce() {
        return countInOnce;
    }

    public void setCountInOnce(Integer countInOnce) {
        this.countInOnce = countInOnce;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getMoreFreeType() {
        return moreFreeType;
    }

    public void setMoreFreeType(String moreFreeType) {
        this.moreFreeType = moreFreeType;
    }

    public String getMoreOutWay() {
        return moreOutWay;
    }

    public void setMoreOutWay(String moreOutWay) {
        this.moreOutWay = moreOutWay;
    }

    public String getPayUserName() {
        return payUserName;
    }

    public void setPayUserName(String payUserName) {
        this.payUserName = payUserName;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCredentialsPic() {
        return credentialsPic;
    }

    public void setCredentialsPic(String credentialsPic) {
        this.credentialsPic = credentialsPic;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getParkTime() {
        return parkTime;
    }

    public void setParkTime(String parkTime) {
        this.parkTime = parkTime;
    }

    public Integer getChargeId() {
        return chargeId;
    }

    public void setChargeId(Integer chargeId) {
        this.chargeId = chargeId;
    }
}