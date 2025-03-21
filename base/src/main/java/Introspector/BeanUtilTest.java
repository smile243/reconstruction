package Introspector;



import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author yjl
 * @Description PropertyUtils类和BeanUtils不同在于，运行getProperty、setProperty操作时，没有类型转换，使用属性的原有类型或者包装类。
 * 由于age属性的数据类型是int，所以方法PropertyUtils.setProperty(userInfo, "age", "8")会爆出数据类型不匹配，无法将值赋给属性。
 * @date 2022/10/26 21:31
 */
public class BeanUtilTest {
    public static void main(String[] args) {
        UserInfo userInfo=new UserInfo();
        try {
            BeanUtils.setProperty(userInfo, "userName", "peida");

            System.out.println("set userName:"+userInfo.getUserName());

            System.out.println("get userName:"+BeanUtils.getProperty(userInfo, "userName"));

            BeanUtils.setProperty(userInfo, "age", "18");
            System.out.println("set age:"+userInfo.getAge());

            System.out.println("get age:"+BeanUtils.getProperty(userInfo, "age"));

            System.out.println("get userName type:"+BeanUtils.getProperty(userInfo, "userName").getClass().getName());
            System.out.println("get age type:"+BeanUtils.getProperty(userInfo, "age").getClass().getName());

            PropertyUtils.setProperty(userInfo, "age", 8);
            System.out.println(PropertyUtils.getProperty(userInfo, "age"));

            System.out.println(PropertyUtils.getProperty(userInfo, "age").getClass().getName());

            PropertyUtils.setProperty(userInfo, "age", "8");
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
