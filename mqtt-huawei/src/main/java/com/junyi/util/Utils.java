package com.junyi.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 各种工具类
 * @time: 2021/9/13 16:08
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class Utils {




    /***
     * 调用sha256算法进行哈希
     */
    public static String sha256_mac(String message, String tStamp) {
        String passWord = null;
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(tStamp.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            passWord = byteArrayToHexString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return passWord;
    }

    /***
     * byte数组转16进制字符串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    /***
     * 要求：10位数字
     */
    public static String getTimeStamp() {
        String timeStamp = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"))
                .format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
        return timeStamp;
    }

    public static String getClientId(String deviceId) {
        return deviceId + "_0_0_" + getTimeStamp();
    }

    public static String getPassword(String secret) {
        return sha256_mac(secret, getTimeStamp());
    }

    //华为模型 0：现金支付  1：POS支付 2：卡支付   3：易通卡支付 4：微信    5：支付宝   6：第三方微信 7：第三方支付宝    100：预约车
    public static Integer getPayType(Integer payType) {
        int type = 0;
        if (payType == null) {
            return type;
        }
        switch (payType) {
            case 0: type = 0; break; // 现金
            case 1: type = 4; break; // 微信
            case 2: type = 5; break; // 支付宝
            case 3: type = 2; break; // 银联闪付
            case 6: type = 0; break; // 其他
            case 9: type = 2; break; // 银联无感
            case 10:  // 招商银行
            case 11:  // 建设银行
            case 12:  // 农业银行
            case 13:  // 交通银行
            case 14:  // 银联
            case 15:  // 聚合Y支付
            case 21:  // ETC支付
            case 16:  // 工商银行
            case 17:  // 平安银行
            case 19:  // 聚合C支付
            case 23:  // 工行聚合付
                type = 6;
        }
        return type;
    }

    //华为模型： 车辆类型。   0：临时车   1：VIP车  2：月租车   3：充值车   4：时租车   5：产权车   6：计次车   7：贵宾卡   8：员工卡   9：大客车   100：预约车
    public static Integer getCarType(Integer carType) {
        int type = 0;
        if (carType == null) {
            return type;
        }
        carType /= 10;
        switch (carType) {
            case 1: type = 2; break;
            case 5: type = 3; break;
            case 3: type = 0; break;
            case 4: type = 1; break;
        }
        return type;
    }



    /**  根据 topic 获取 requestId */
    public static String getRequestId(String topic) {
        String requestId = "";
        try {
            int index = topic.indexOf("request_id=");
            requestId = topic.substring(index + "request_id=".length());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestId;
    }
}
