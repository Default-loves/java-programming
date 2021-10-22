package com.junyi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @time: 2021/10/16 15:40
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@RestController
public class MyController {

    @Autowired
    MyService service;

    @GetMapping("/click")
    public Book click() {
        return service.click();
    }
}
