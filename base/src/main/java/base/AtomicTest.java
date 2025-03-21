package base;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**统计0的个数
 * @author yjl
 * @since 2024/5/31
 */
public class AtomicTest {
    private static  AtomicLong atomicLong = new AtomicLong();
    private static final Integer[] arrayOne = new Integer[]{0, 1, 2, 3, 0, 5, 6, 0, 56, 0};
    private static final Integer[] arrayTwo = new Integer[]{10, 1, 2, 3, 0, 5, 6, 0, 56, 0};
    private final static CountDownLatch countDownLatch = new CountDownLatch(2);

    private static void run(Integer[] array) {
        int size = array.length;
        for (Integer integer : array) {
            if (integer == 0) {
                atomicLong.incrementAndGet();
            }
            //由于数据量小执行太快了，加这段更好触发并发
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        countDownLatch.countDown();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(() -> run(arrayOne));
        Thread two = new Thread(() -> run(arrayTwo));
        one.start();
        two.start();
        //等待线程执行完毕
        countDownLatch.await();
        System.out.println(atomicLong);
    }


}
