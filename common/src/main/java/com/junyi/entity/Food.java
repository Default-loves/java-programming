package com.junyi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @time: 2021/2/4 10:40
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    private Integer id;
    private String name;
    private Double money;
}
