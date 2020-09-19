package com.junyi.handle;

import lombok.Data;

import java.util.Optional;

/**
 * @time: 2020/9/4 11:31
 * @version: 1.0
 * @author: junyi Xu
 * @description: 一个 JSON 数据层次性比较深，可能有4,5层，需要对里层的数据进行获取，为了避免空指针，使用 Optional，否则需要对集合类型对象逐一判断是否为null
 */
public class getDataFromComplexJson {

    public String[] getData(Outer outer) {
        return Optional.ofNullable(outer)
                .map(Outer::getMiddle)
                .map(Middle::getInner)
                .map(o -> new String[]{String.valueOf(o.getInner_id()), o.getName()})
                .orElse(null);
    }

    @Data
    class Outer {
        private Integer id;
        private Middle middle;
    }
    @Data
    class Middle {
        private Integer mid_id;
        private Inner inner;
    }
    @Data
    class Inner {
        private Integer inner_id;
        private String name;
    }
}
