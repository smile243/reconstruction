package util;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author yjl
 */
public class BigDecimalUtil {

    public  static BigDecimal calDivide(BigDecimal dividend,BigDecimal divisor){
        if(BigDecimal.ZERO.compareTo(divisor)==0){
            return BigDecimal.ZERO;
        }
        return dividend.divide(divisor, 2, RoundingMode.HALF_UP);
    }

    public  static BigDecimal calDivideWithScale(BigDecimal dividend,BigDecimal divisor,int scale){
        if(BigDecimal.ZERO.compareTo(divisor)==0){
            return BigDecimal.ZERO;
        }
        return dividend.divide(divisor, scale, RoundingMode.HALF_UP);
    }

    /**
     *
     * @param obj 对象
     * @param func 对象属性操作
     * @return 当前对象或者属性为空，返回0
     */
    public static<T> BigDecimal notNullGet(T obj, Function<T,BigDecimal> func){
        if(Objects.nonNull(obj)&&Objects.nonNull(func.apply(obj))){
            return func.apply(obj);
        }
        return BigDecimal.ZERO;
    }

    /**
     *
     * @param val 数值
     * @return null时返回0
     */
    public static BigDecimal notNullGet(BigDecimal val){
        return Objects.nonNull(val)?val:BigDecimal.ZERO;
    }
}
