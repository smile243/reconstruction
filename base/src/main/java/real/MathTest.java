package real;

/**
 * @Description: 异或可用于交换两数值
 * @author: yjl
 * @date: 2022年11月28日 13:29
 */
public class MathTest {
    public void testChange(Integer a,Integer b){
        a=a^b;
        b=a^b;
        a=a^b;
    }
    public static void main(String[] args) {
        MathTest test=new MathTest();
        test.testChange(1,1);
    }
}
