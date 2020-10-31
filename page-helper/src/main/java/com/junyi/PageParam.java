package com.junyi;

import com.github.pagehelper.IPage;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PageParam<T>  implements IPage {

    //  description = "页码", defaultValue =  1
    private Integer pageNum = 1;

    // description = "页数", defaultValue = 20
    private Integer pageSize = 20;

    // description = "排序", example = "id desc"
    private String orderBy;

    //  description = "参数"
    private T param;

    public PageParam<T> setOrderBy(String orderBy) {
        this.orderBy = orderBy; // 此处可优化 优化详情且看解析
        return this;
    }

    @Override
    public Integer getPageNum() {
        return pageNum;
    }

    @Override
    public Integer getPageSize() {
        return pageSize;
    }

    @Override
    public String getOrderBy() {
        return orderBy;
    }
}
