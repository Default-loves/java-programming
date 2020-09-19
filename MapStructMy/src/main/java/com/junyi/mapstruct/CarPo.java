package com.junyi.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @time: 2020/9/8 11:27
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarPo {
    private Integer id;
    private String brand;
    private String carName;
    private Date createDate;
}