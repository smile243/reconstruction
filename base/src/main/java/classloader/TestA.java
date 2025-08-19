package classloader;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @author: yjl
 * @date: 2023年03月02日 14:56
 */

public class TestA {
    public static void main(String[] args) {
        TestA testA = new TestA();
        testA.hello();
    }

    public void hello() {
        System.out.println(("TestA: " + this.getClass().getClassLoader()));
        TestB testB = new TestB();
        testB.hello();
    }
}
