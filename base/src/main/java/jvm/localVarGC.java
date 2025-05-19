package jvm;

/**
 * -XX:+PrintGC查看gc情况
 * @author yjl
 * @since 2025/5/7
 */
public class localVarGC {
    public void localvarGc1(){
        byte[] a = new byte[6*1024*1024];
        System.gc();
    }
    public void localvarGc2(){
        byte[] a = new byte[6*1024*1024];
        a = null;
        System.gc();
    }
    public void localvarGc3(){
        {byte[] a = new byte[6*1024*1024];}
        System.gc();
    }
    public void localvarGc4(){
        {byte[] a = new byte[6*1024*1024];}
        //变量c会复用a的字，所以gc可以回收
        int c= 10;
        System.gc();
    }
    public void localvarGc5(){
        //此函数调用完成，它的帧都会销毁
        localvarGc1();
        //gc1中的byte会随着localvarGc1函数调用完销毁，所以gc也可以回收
        System.gc();
    }

    public static void main(String[] args) {
        localVarGC test = new localVarGC();
        test.localvarGc5();
    }
}
