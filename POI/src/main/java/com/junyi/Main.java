package com.junyi;

import org.junit.jupiter.api.Test;

/**
 * @time: 2021/6/9 11:23
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class Main {

    @Test
    public void test() {
        Float data = 1.5f;
        String res = transfer(null);
        System.out.println(res);
    }

    public String transfer(Object object) {
        String result = "-";
        if (object == null) {
            return result;
        }
        try {
            result = String.valueOf(object);
        } catch (Exception e) {

        }
        return result;
    }
}
