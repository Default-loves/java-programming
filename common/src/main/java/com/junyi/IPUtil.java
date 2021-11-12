package com.junyi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * ip 工具类
 * @time: 2021/11/9 10:43
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class IPUtil {

    private static final Logger logger = LoggerFactory.getLogger(IPUtil.class);

    /** 获取公网 ip 地址，会通过两种方法尝试获取 */
    public static String getPublicIP() throws MalformedURLException {
        String ip = getPublicIP1();
        if (!StringUtils.isEmpty(ip)) {
            return ip;
        }
        return getPublicIP2();
    }

    /** 获取公网 ip 地址：方法一 */
    public static String getPublicIP1() throws MalformedURLException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        try(BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()))) {
            return in.readLine();
        }
        catch (Exception e){
            logger.error("公网IP获取失败：", e);
            return "";
        }
    }

    /** 获取公网 ip 地址：方法二 */
    public static String getPublicIP2() {
        String url = "http://www.net.cn/static/customercare/yourip.asp";
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByTag("h2");
            for (Element element:elements){
                if(4 == element.text().split("\\.").length) {
                    return element.text();
                }
            }
        } catch (IOException e) {
            logger.error("error: ",e);
        }
        return null;
    }
}
