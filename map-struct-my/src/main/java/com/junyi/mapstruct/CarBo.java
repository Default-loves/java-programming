package com.junyi.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @time: 2020/9/8 12:09
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarBo {
    private Integer id;
    private String brand;
    private String carName;
    private Date createTime;
    private double price;
    private String color;
}