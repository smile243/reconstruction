package concurrent;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 有的线程它死了，于是它变成一道面试题
 * question: 一个线程池中的线程异常了，那么线程池会怎么处理这个线程?
 * 答:执行方式为execute，抛出堆栈异常，submit的话返回结果封装在future中，如果调用future.get()方法则必须进行异常捕获，从而可以抛出(打印)堆栈异常，否则不会抛异常
 * 不影响其他线程任务
 * 线程池会把这个线程移除掉，并创建一个新的线程放到线程池中
 *
 * @author yjl
 */
public class ExecutorsTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executorService = buildThreadPoolTaskExecutor();
        /***
         * way1
         */
//        executorService.execute(() -> sayHi("execute"));
//        executorService.submit(() -> sayHi("submit"));
        /***
         * way2
         */
        //executorService.execute(() -> sayHi("execute"));
        TimeUnit.MILLISECONDS.sleep(10);
        System.out.println("====================");
        Future<?> submit = executorService.submit(() -> sayHi("submit"));
//        try {
//            submit.get();
//        }catch (ExecutionException e){
//            e.printStackTrace();
//        }
    }

    private static void sayHi(String name) {
        String printStr = "【thread-name:" + Thread.currentThread().getName() + ",执行方式:" + name + "】";
        System.out.println(printStr);
        throw new RuntimeException(printStr + ",我异常啦!哈哈哈!");
    }

    private static ThreadPoolExecutor buildThreadPoolTaskExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
            10,
            30,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(1000),
            new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}

