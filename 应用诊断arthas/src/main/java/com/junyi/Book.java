package com.junyi;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @time: 2021/10/16 16:07
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Data
@Builder
public class Book {

    private Integer id;
    private String name;
    private LocalDateTime publishTime;

}
