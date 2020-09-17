package com.junyi;



import org.springframework.beans.BeanUtils;

import java.io.*;
import java.util.UUID;

/**
 * User: JY
 * Date: 2019/1/23 0023
 * Description:
 */



public class aaa {
    public static void main(String[] args) {
        String id1 = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String id2 = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        System.out.println(id1);
        System.out.println(id2);
        BeanUtils.copyProperties();


    }

}
