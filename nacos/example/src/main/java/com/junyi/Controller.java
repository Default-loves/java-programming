package com.junyi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @time: 2021/1/21 17:13
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@RestController
@RefreshScope
public class Controller {

    @Value("${food.name: default}")
    private String content;

    @GetMapping("/hello")
    public String hello() {
        return content;
    }
}
