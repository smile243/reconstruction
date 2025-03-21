package jvm;

/**
 * @Description: 单线程下 无论栈帧太大还是虚拟机栈容量太小，都抛出StackOverflowError
 * @author: yjl
 * @date: 2021年10月19日 14:50
 */
public class JVMStackSOFTest {
    private int stackLength=1;
    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JVMStackSOFTest oom=new JVMStackSOFTest();
        try {
            oom.stackLeak();
        }catch (Throwable e){
            System.out.println("stack length: "+oom.stackLength);
            throw e;
        }
    }
}
