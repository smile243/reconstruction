package jvm;

/**
 * @Description: 栈帧
 * @author: yjl
 * @date: 2022年02月07日 10:11
 */
public class StackFrameTest {
    public static void main(String[] args) {
        StackFrameTest test=new StackFrameTest();
        test.method1();

    }
    public void method1(){
        System.out.println("method1开始执行...");
        method2();
        System.out.println("method1执行结束...");
    }
    public int method2(){
        System.out.println("method2开始执行...");
        int i=(int)method3();
        System.out.println("method2即将结束...");
        return i+10;
    }

    private double method3() {
        System.out.println("method3开始执行...");
        double i=20.0;
        System.out.println("method3即将结束...");
        return i;
    }
}
