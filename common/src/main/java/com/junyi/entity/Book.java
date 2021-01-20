package com.junyi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @time: 2020/9/22 16:19
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer id;
    private String name;
    private Double price;
    private List<String> stringList;
    private LocalDateTime time;
    private Shop shop;
}
