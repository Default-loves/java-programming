package com.junyi;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @time: 2021/6/18 15:17
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {
    private Integer id;
    @JsonAlias("type")
    @JsonProperty("type")
    private String name;
    private String phone;
}
