package jvm;

/**
 * @Description: 帧栈-操作数栈
 * @author: yjl
 * @date: 2022年02月09日 10:45
 */
public class OperandStackTest {
    public void testAddOperation(){
        byte i=15;
        int j=8;
        int k=i+j;
    }
    public int getSum(){
        int m=10;
        int n=20;
        int k=m+n;
        return k;
    }
    public void testGetSum(){
        //获取上一个栈帧返回的结果，并保存在操作数栈中
        int iu=getSum();
        int j=10;
    }
}
