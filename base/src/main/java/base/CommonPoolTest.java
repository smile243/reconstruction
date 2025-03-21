package base;

/** 常量池,关键在于各包装类型的value of方法
 * @author yjl
 * @since 2024/5/31
 */
public class CommonPoolTest {
    public static void main(String[] args) {
        //相当于Integer i1=Integer.valueOf(40) ,i1引用的是常量池的对象
        Integer i1 = 40;
        //i2引用的是创建的对象
        Integer i2 = new Integer(40);
        System.out.println(i1==i2);
    }
}
