package real;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author yjl
 * @since 2024/11/11
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectUtil {

    public static<T,E> E notNullGet(T obj, Function<T,E> func){
        if(Objects. nonNull(obj)&&Objects. nonNull(func)){
            return func.apply(obj);
        }
        return null;
    }

}
