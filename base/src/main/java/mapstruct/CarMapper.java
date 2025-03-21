package mapstruct;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @Description: 1.属性名相同，不需要标注，隐式转换  @BeanMapping(ignoreByDefault = true)可以关闭隐式转换
 * 2.属性必须有get,set方法
 * @author: yjl
 * @date: 2024年04月28日 13:47
 */
@Mapper
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mapping(source = "price", target = "amount")
    @BeanMapping(ignoreByDefault = true)
    CarDTO carToCarDTO(Car car);

    @Mapping(source = "amount", target = "price")
    @Mapping(source = "sort", target = "type")
    Car carDTOToCar(CarDTO carDTO);

}
