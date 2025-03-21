package jvm;

/**
 * @Description: 动态链接
 * @author : yjl
 * @date: 2022年02月09日 11:25
 */
public class DyncmicLinkingTest{
    int num;
    public void methodA(){
        System.out.println("methodA()...");
    }
    public void methodB(){
        System.out.println("methodB()...");
        methodA();
        num++;
    }
}
