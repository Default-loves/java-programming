package com.junyi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import com.junyi.entity.tmp.CouponVM;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @time: 2020/12/25 15:48
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    @Excel(name = "id", needMerge = true, orderNum = "3")
    private Integer id;
    @Excel(name = "描述", orderNum = "2")
    private String description;
    @Excel(name = "标签", orderNum = "1")
    private String tag;
    @ExcelCollection(name = "优惠", orderNum = "3")
    private List<CouponVM> couponVMList;


}
