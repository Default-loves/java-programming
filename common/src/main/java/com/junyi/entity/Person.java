package com.junyi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @time: 2021/1/9 8:10
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private Integer id;
    @Excel(name = "名字")
    private String name;
    @Excel(name = "日期")
    private LocalDateTime createDateTime;
}
