package com.junyi;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * @time: 2020/11/19 15:28
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class Pit {

    public void ListIsNull() {
        ArrayList<Integer> list = null;
        for (Integer t: list) {
            log.info(t + "");
        }
    }
}
