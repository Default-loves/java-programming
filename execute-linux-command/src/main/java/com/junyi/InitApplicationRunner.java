package com.junyi;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @time: 2021/7/23 15:49
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Component
public class InitApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> resultList = null;
        resultList = CommandUtil.runCommand("pwd", (textList, text) -> {
            textList.add(text);
        });
        System.out.println(resultList);
    }
}
