package com.junyi.mapstruct;

import com.junyi.mapstruct.CarBo.CarBoBuilder;
import com.junyi.mapstruct.CarVo.CarVoBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-08T13:49:02+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
public class CarCovertBasicImpl implements CarCovertBasic {

    @Override
    public CarVo toConvertVo(CarPo source) {
        if ( source == null ) {
            return null;
        }

        CarVoBuilder carVo = CarVo.builder();

        carVo.name( source.getCarName() );
        carVo.id( source.getId() );
        carVo.brand( source.getBrand() );

        carVo.createDate( source.getCreateDate().toString() );

        return carVo.build();
    }

    @Override
    public List<CarVo> toConvertVos(List<CarPo> source) {
        if ( source == null ) {
            return null;
        }

        List<CarVo> list = new ArrayList<CarVo>( source.size() );
        for ( CarPo carPo : source ) {
            list.add( toConvertVo( carPo ) );
        }

        return list;
    }

    @Override
    public CarBo toConvertBo(CarPo source1, AttributePo source2) {
        if ( source1 == null && source2 == null ) {
            return null;
        }

        CarBoBuilder carBo = CarBo.builder();

        if ( source1 != null ) {
            carBo.id( source1.getId() );
            carBo.brand( source1.getBrand() );
            carBo.carName( source1.getCarName() );
        }
        if ( source2 != null ) {
            carBo.price( source2.getPrice() );
            carBo.color( source2.getColor() );
        }

        return carBo.build();
    }
}
