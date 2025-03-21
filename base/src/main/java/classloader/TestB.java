package classloader;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @author: yjl
 * @date: 2023年03月02日 14:56
 */
@Slf4j
public class TestB {
    public void hello() {
        log.info("TestB: " + this.getClass().getClassLoader());
    }
}
