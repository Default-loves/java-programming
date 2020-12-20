package com.junyi.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @time: 2020/9/8 11:27
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Mapper
public interface CarCovertBasic {
    CarCovertBasic INSTANCE = Mappers.getMapper(CarCovertBasic.class);

    @Mappings({
        @Mapping(source = "carName", target = "name"),
        @Mapping(target = "createDate", expression = "java(source.getCreateDate().toString())")
    })
    CarVo toConvertVo(CarPo source);

    List<CarVo> toConvertVos(List<CarPo> source);

    CarBo toConvertBo(CarPo source1, AttributePo source2);
}