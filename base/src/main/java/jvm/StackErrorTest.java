package jvm;

/**
 * @Description: 设置栈大小 -Xss
 * @author: yjl
 * @date: 2022年01月29日 11:01
 */
public class StackErrorTest {
    private static int count=1;
    public static void main(String[] args) {
        System.out.println(count);
        count++;
        main(args);
    }
}
