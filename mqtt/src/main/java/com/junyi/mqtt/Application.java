package com.junyi.mqtt;

import com.junyi.mqtt.sample.ApplicationListenerMy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
/**
 *
 */

/**
 * @time: 2020/10/14 16:28
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@SpringBootApplication
@IntegrationComponentScan
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        context.addApplicationListener(new ApplicationListenerMy());
    }
}
