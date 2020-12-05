package com.junyi.mqtt.util;

/**
 * @time: 2020/11/19 8:46
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class NormalUtil {



    public static String getEvenType(String type) {
        switch (type) {
            case "1": return "入场";
            case "5": return "出场";
            case "9": return "无牌车入场";
            case "10": return "无牌车出场";
            default: return "default";
        }
    }

    public static String getInOutType(String type) {
        switch (type) {
            case "001": return "大车场入口";
            case "002": return "大车场出口";
            case "003": return "小车场入口";
            case "004": return "小车场出口";
            default: return "default loc";
        }
    }
}
