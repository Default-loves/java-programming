package com.junyi.util;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * @time: 2021/9/16 17:50
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class SecurityUtil {


    /**
     * 加载SSL证书
     * @param fileName: 文件名
     * @return
     */
    public static SocketFactory getOptionSocketFactory(String fileName, InputStream inputStream) {
        SSLContext sslContext;

        try {
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            KeyStore keyStore = getKeyStore(fileName, inputStream);
            tmf.init(keyStore);
            TrustManager[] tm = tmf.getTrustManagers();

            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tm, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sslContext.getSocketFactory();
    }


    /**
     * 加载SSL证书
     * @param certPath 证书存放的路径
     * @return
     */
    public static SocketFactory getOptionSocketFactory(String certPath) {
        SSLContext sslContext;

        try {
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            KeyStore keyStore = getKeyStore(certPath);
            tmf.init(keyStore);
            TrustManager[] tm = tmf.getTrustManagers();

            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tm, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sslContext.getSocketFactory();
    }



    private static KeyStore getKeyStore(String certPath) {
        try(InputStream inputStream = new FileInputStream(certPath)) {
            return getKeyStore(certPath, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    private static KeyStore getKeyStore(String fileName, InputStream inputStream) {
        if (fileName.endsWith("jks")) {
            return getFromJks(inputStream);
        } else if (fileName.endsWith("pem")) {
            return getFromPem(inputStream);
        }
        return null;
    }

    /** 读取 jks 文件 */
    private static KeyStore getFromJks(InputStream inputStream) {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(inputStream, null);
            return keyStore;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 读取 pem 文件 */
    public static KeyStore getFromPem(InputStream inputStream) {
        KeyStore keyStore = null;
        try {
            Certificate cert = null;
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            cert = cf.generateCertificate(inputStream);
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("certificate", cert);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyStore;
    }

}
