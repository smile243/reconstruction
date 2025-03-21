package orika;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * @author yjl
 * @since 2024/6/28
 */
public class Util {
    private static final MapperFactory factory = new DefaultMapperFactory.Builder().build();
    private static final MapperFacade facade = factory.getMapperFacade();

    public static MapperFactory getFactory() {
        return factory;
    }

    /**用于dto ->entity的转换
     */
    public static MapperFacade getFacade() {
        return facade;
    }
}
