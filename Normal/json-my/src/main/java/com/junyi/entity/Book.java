package com.junyi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @time: 2020/9/4 11:56
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Data
public class Book {
    private Integer id;
    @JsonProperty("b_name")
    private String name;
}
