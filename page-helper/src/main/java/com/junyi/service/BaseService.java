package com.junyi.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.junyi.entry.PageParam;

import java.util.List;

public interface BaseService<Param, Result> {

    /**
     * 分页查询
     *
     * @param param 请求参数DTO
     * @return 分页集合
     */
    default PageInfo<Result> page(PageParam<Param> param) {
        return PageHelper.startPage(param).doSelectPageInfo(() -> list(param.getParam()));
    }

    /**
     * 集合查询
     *
     * @param param 查询参数
     * @return 查询响应
     */
    List<Result> list(Param param);
}
