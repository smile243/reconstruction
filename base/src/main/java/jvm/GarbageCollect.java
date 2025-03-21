package jvm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @author: yjl
 * @date: 2022年10月17日 9:08
 */
public class GarbageCollect {
    public static void main(String[] args) {
        method();
        System.gc();
    }

    public static void method(){
        ExecutorService executorService=Executors.newFixedThreadPool(3);
        executorService.execute(()->{
            System.out.println("Thread.currentThread.getName ="+Thread.currentThread().getName());
        });
        System.out.println("executorService"+ executorService);
    }
}
