package com.junyi;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @time: 2021/3/26 11:50
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@SpringBootApplication
public class Local implements ApplicationRunner {



    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Local.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CallBackMy callBack = new CallBackMyImpl();
        Remote remote = new Remote();
        remote.setCallBack(callBack);
        remote.execute();
    }
}
