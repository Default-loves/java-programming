package com.junyi.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @time: 2020/9/8 12:08
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributePo {
    private double price;
    private String color;
}