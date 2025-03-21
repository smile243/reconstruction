package jvm;

/**
 * @Description: 任何一个类声明以后，内部至少有一个类构造器对应 <init>方法
 * @author: yjl
 * @date: 2022年01月27日 13:42
 */
public class ClinitTest {
    private int a=1;

    /***
     *无(静态代码块和变量赋值)是不会有<clinit>方法的的
     */
    private static int c=2;
    public static void main(String[] args) {
        int b=2;
    }
}
