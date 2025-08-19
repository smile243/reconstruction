package classloader;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @author: yjl
 * @date: 2023年03月02日 14:56
 */

public class TestB {
    public void hello() {
        System.out.println("TestB: " + this.getClass().getClassLoader());
    }
}
