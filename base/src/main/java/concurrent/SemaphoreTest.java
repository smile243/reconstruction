package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 作用:控制并发量
 * 应用场景:流量控制
 *
 * @author yjl
 */
public class SemaphoreTest {
    private static final int THREAD_COUNT = 30;
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
    //只允许10个并发执行
    private static final Semaphore s = new Semaphore(10);

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        s.acquire();
                        System.out.println("save mysql data");
                        Thread.sleep(5000);
                        s.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
    }
}
