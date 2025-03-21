package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author yjl
 * @Description
 * @date 2022/11/1 21:12
 */
public class SerializeTest {
    public void testA() {
        Employee e = new Employee();
        e.name = "zhangsan";
        e.address = "beiqinglu";
        e.age = 20;

        try {
            // 创建序列化流对象
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("employee.txt"));
            // 写出对象
            out.writeObject(e);
            // 释放资源
            out.close();
            System.out.println("Serialized data is saved");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void testB() {
        Employee e = null;
        try {
            // 创建反序列化流
            FileInputStream fileIn = new FileInputStream("employee.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            // 读取一个对象
            e = (Employee) in.readObject();
            // 释放资源
            in.close();
            fileIn.close();
        } catch (IOException i) {
            // 捕获其他异常
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            // 捕获类找不到异常
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
        // 无异常,直接打印输出
        System.out.println("Name: " + e.name);
        System.out.println("Address: " + e.address);
        System.out.println("age: " + e.age);
    }

    public static void main(String[] args) {
        SerializeTest test = new SerializeTest();
        test.testB();
    }
}
