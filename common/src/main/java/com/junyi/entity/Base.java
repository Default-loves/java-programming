package com.junyi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @time: 2021/1/9 8:09
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Base {
    private Integer id;
    private LocalDateTime createDateTime;
}
