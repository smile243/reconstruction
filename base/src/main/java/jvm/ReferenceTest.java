package jvm;


import java.lang.ref.SoftReference;

/**
 * 弱引用已经发现，立即回收
 */
public class ReferenceTest {
    static class Student {
        byte[] big = new byte[1024 * 512]; // 模拟占用内存
    }
    public static void main(String[] args) throws InterruptedException {
        //强引用，除非再次设置为null，否则不会被系统回收
        StringBuffer sb = new StringBuffer("hello");

        //软引用
        Student student = new Student();
        //虽然软引用会在内存不足的情况下回收，但是不稳定
        SoftReference<Student> softReference = new SoftReference<>(student);
        student = null;
        System.out.println(softReference.get());
        System.gc();
        System.out.println("after gc:");
        System.out.println(softReference.get());
        //尝试占用内存
        byte[] b = new byte[1024 * 925 * 7];
        System.gc();
        System.out.println(softReference.get());
    }
}
