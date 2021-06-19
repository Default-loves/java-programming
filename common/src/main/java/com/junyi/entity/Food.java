package com.junyi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @Excel(name = "id", needMerge = true)
    private Integer id;
    @Excel(name = "name", needMerge = true)
    private String name;
    @Excel(name = "money", needMerge = true)
    private Double money;
    @ExcelCollection(name = "shops")
    private List<Shop> shops;
}
