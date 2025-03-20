package concurrent;

import com.alibaba.ttl.threadpool.TtlExecutors;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 串行关系:thenApply、thenAccept、thenRun 和 thenCompose
 * AND汇聚关系:thenCombine、thenAcceptBoth 和 runAfterBoth
 * OR汇聚关系:applyToEither、acceptEither 和 runAfterEither
 * 异常:exceptionally->catch,whenComplete->finally和 handle->finally
 *
 * @author yjl
 */
@Slf4j
public class CompleteFutureTest2 {
    public static Executor getFutureExecutor() {
        int cpuNums = Runtime.getRuntime().availableProcessors();
        log.info("CPU数量:{}", cpuNums);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
            10,
            20,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(3000),
            new ThreadPoolExecutor.CallerRunsPolicy());
        return TtlExecutors.getTtlExecutor(executor);
    }

    public static String methodA() {
        System.out.println("A任务开始执行");
        int x = 1 / 0;
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        return "A任务完成";
    }

    public static String methodB() {
        System.out.println("B任务开始执行");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "B任务完成";
    }

    public static String methodC() {
        System.out.println("C任务开始执行");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "C任务完成";
    }

    public static void main(String[] args) {
        //supplyAsync有返回值
        //runAsync无返回值
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(CompleteFutureTest2::methodA, getFutureExecutor())
            .exceptionally(exception -> {
                log.error("异常报错");
                throw new RuntimeException("报错了");
            });
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(CompleteFutureTest2::methodB, getFutureExecutor());
        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(CompleteFutureTest2::methodC, getFutureExecutor());
        //等待cf1、cf2执行完成
//        cf1.thenCombine(cf2,(r1,r2)->{
//            System.out.println(r1);
//            System.out.println(r2);
//            return "";
//        }).thenAccept(System.out::println).join();
        //主线程等待所有异步任务执行完成后结束
        try {
            CompletableFuture.allOf(cf1, cf2, cf3).join();
        } catch (Exception e) {
            System.out.println("123");
            throw new RuntimeException(e.getMessage());
        }

    }
}
