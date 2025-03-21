package mapstruct;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @Description:
 * @author: yjl
 * @date: 2024年04月28日 14:23
 */
public class test {
    public static void main(String[] args) {
        CarDTO carDTO = new CarDTO();
        carDTO.setBrand("品牌");
        carDTO.setAmount(new BigDecimal("500"));
        carDTO.setWheels(Arrays.asList("前左车胎", "前右车胎", "后左车胎", "后右车胎"));
        carDTO.setSort(3);
        Car car = CarMapper.INSTANCE.carDTOToCar(carDTO);
        CarDTO carDTO2 = CarMapper.INSTANCE.carToCarDTO(car);
        System.out.println(car);
        System.out.println(carDTO2);
    }
}
