package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 用照相机定位不显示的错误信息
 *
 * @author yjl
 */
public class ExecutorTest {
    public static void main(String[] args) throws InterruptedException {

        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            tasks.add(() -> {
                System.out.println("callable " + finalI);
                Thread.sleep(500);
                return null;
            });
        }

//        ExecutorService executor = Executors.newFixedThreadPool(2);
        /***
         * 改版后的自定义线程池
         * 线程池拒绝策略如果是静默处理,invokeAll方法回捕获异常，不抛出，也cancel不了，所以队列会一直阻塞
         */
        ExecutorService executor = new ThreadPoolExecutor(
            1,
            3,
            1,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.DiscardPolicy()
        );
        Thread executorInvokerThread = new Thread(() -> {
            try {
                executor.invokeAll(tasks);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("invokeAll returned");
        });
        executorInvokerThread.start();

        Thread.sleep(800);
        System.out.println("shutdown");

        /***
         * 不这样写，程序不会中断
         */
        List<Runnable> runnables = executor.shutdownNow();
        for (Runnable r : runnables) {
            if (r instanceof Future) ((Future<?>) r).cancel(false);
        }
        System.out.println("shutdown complete");
    }
}
