package jvm;

/**
 * @author yjl
 * @Description
 * -XX:+PrintGCDetails
 * -Xms25m
 * -Xmx25m
 * @date 2023/2/7 21:19
 */
public class GCTest {
    public static void main(String[] args) {
        byte[] allocation1, allocation2;
        allocation1 = new byte[1024*3900];
        //allocation2 = new byte[900*1024];
    }
}
