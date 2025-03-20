package concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 出现的原因是因为join实现主线程等待子线程的方式满足的场景有限
 * countDown会使aqs的state减1
 * await直到state为0才会返回
 * countDownLatch方法调用必须在finally中，为了子线程发现异常，主线程不受干扰
 *
 * @author yjl
 */
public class CountDownLatchTest {
    private static volatile CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("child thread one over!");
                countDownLatch.countDown();
            }
        });
        executorService.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("child thread two over!");
                countDownLatch.countDown();
            }
        });
        System.out.println("wait all child thread begin");

        countDownLatch.await();

        System.out.println("all child thread over");
        executorService.shutdown();
    }

}
