package com.junyi.basis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * @time: 2021/4/15 10:36
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class RandomMy {

    @Test
    public void test() {
        Random random = new Random(1);      // 对于固定了起始的seed，那么每次程序运行后，生成的随机数序列都是一样的
        for (int i = 0; i < 10; i++) {
            int k = random.nextInt(5);      // 取值范围：[0, 5), 左闭右开
            log.info("generate number: {}", k);
        }
    }
}
