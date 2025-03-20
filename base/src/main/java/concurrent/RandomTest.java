package concurrent;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 多线程下减少种子变量的竞争
 *
 * @author yjl
 */
public class RandomTest {
    public static void main(String[] args) {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

        for (int i = 0; i < 10; i++) {
            System.out.println(threadLocalRandom.nextInt(10));
        }
    }
}
