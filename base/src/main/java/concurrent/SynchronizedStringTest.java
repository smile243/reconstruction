package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yjl
 */
public class SynchronizedStringTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            String a = "123";
            executorService.execute(() -> {
                //如果锁的字符串在编译器不确定，即使相同也不会锁住，得用intern();
                synchronized (a) {
                    System.out.println(Thread.currentThread().getName() + "开始");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "结束");
                }
            });
        }
    }
}
