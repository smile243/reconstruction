package base;

/**
 * @author yjl
 * @Description
 * @date 2022/7/22 22:41
 */
public class InstanceOfTest<T> {
    public boolean instanceOf(Object obj){
        boolean result;
        if (obj == null) {
            result = false;
        } else {
            try {
                T temp = (T) obj;
                result = true;
            } catch (ClassCastException e) {
                result = false;
            }
        }
        return result;
    }
    public static void main(String[] args) {
        Integer a=1;
        System.out.println(a instanceof Integer);
    }
}
