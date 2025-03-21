package io;

import java.io.Serializable;

/**
 * @author yjl
 * @Description
 * @date 2022/11/1 21:13
 */
public class Employee implements Serializable {
    // 加入序列版本号
    private static final long serialVersionUID = 1L;
    public String name;
    public String address;
    // transient瞬态修饰成员,不会被序列化
    public transient int age;

    public void addressCheck() {
        System.out.println("Address  check : " + name + " -- " + address);
    }
}
