package com.junyi.mapstruct;

import java.util.ArrayList;
import java.util.Date;

/**
 * @time: 2020/9/8 11:24
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class Application {
    public static void main(String[] args) {


        CarPo carPo = CarPo.builder()
                .id(1)
                .brand("BMW")
                .carName("宝马")
                .createDate(new Date())
                .build();
        CarVo carVo = CarCovertBasic.INSTANCE.toConvertVo(carPo);

        System.out.println(carVo);

        CarPo carPo1 = CarPo.builder()
                .id(2)
                .brand("abc")
                .carName("丰田")
                .createDate(new Date())
                .build();
        ArrayList<CarPo> list = new ArrayList<>();
        list.add(carPo);
        list.add(carPo1);
        System.out.println(CarCovertBasic.INSTANCE.toConvertVos(list));

        AttributePo attributePo = AttributePo.builder()
                .color("Yellow")
                .price(1.1)
                .build();
        System.out.println(CarCovertBasic.INSTANCE.toConvertBo(carPo, attributePo));


    }

}
