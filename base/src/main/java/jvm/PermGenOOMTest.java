package jvm;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 动态去生成类会导致永久代空间不足
 * @author: yjl
 * @date: 2024年05月23日 14:25
 */
public class PermGenOOMTest {
    public static void main(String[] args) {
        List<Class<?>> classes = new ArrayList<>();

        // 无限循环，持续生成类
        for (int i = 0; ; i++) {
            try {
                // 使用动态代理生成新的类
                Class<?> proxyClass = Proxy.getProxyClass(
                        PermGenOOMTest.class.getClassLoader(),
                        MyInterface.class
                );
                classes.add(proxyClass);
                System.out.println("Generated class: " + proxyClass.getName());
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    // 一个简单的接口，用来生成代理类
    interface MyInterface {
        void myMethod();
    }
}
