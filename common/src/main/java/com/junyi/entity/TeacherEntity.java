package com.junyi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @time: 2021/5/28 17:58
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Data
public class TeacherEntity {
    /**
     * 学生姓名
     */
    @Excel(name = "教师姓名", height = 20, width = 30, isImportField = "true_st")
    private String name;
    /**
     * 学生性别
     */
    @Excel(name = "教师性别", replace = {"男_1", "女_2"}, suffix = "生", isImportField = "true_st")
    private int sex;
}
