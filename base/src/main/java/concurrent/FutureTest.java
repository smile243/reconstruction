package concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author yjl
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executor = new ThreadPoolExecutor(
            1,
            3,
            1,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.DiscardPolicy()
        );
        Future future = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5000);
                return "yjl";
            }
        });
        //停止执行任务,参数代表执行次任务的线程是否应该被中断
        future.cancel(false);
        System.out.println(future.isCancelled());
        //中断,异常，取消都算done
        System.out.println(future.isDone());
        //System.out.println(future.get(3,TimeUnit.SECONDS));
        executor.shutdown();
        System.out.println("结束");
    }
}
