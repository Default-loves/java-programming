package com.junyi.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class CarVo {
    private Integer id;
    private String brand;
    private String name;
    private String createDate;

}
