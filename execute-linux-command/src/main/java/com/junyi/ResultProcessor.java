package com.junyi;

import java.util.List;

/**
 * @time: 2021/7/23 15:38
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public interface ResultProcessor {
    void process(List<String> textList, String text);
}
