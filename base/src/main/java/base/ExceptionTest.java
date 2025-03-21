package base;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 
 * @author: yjl
 * @date: 2021年09月23日 11:41
 */
public class ExceptionTest {
    //Thread.dispatchUncaughtException方法会帮我们打印日志
//    public static void main(String[] args) {
//        int a=1/0;
//    }

//public static void main(String[] args) {
//    try {
//        int a=1/0;
//    }catch (Exception e){
//        e.printStackTrace();
//    }
//}

//public static void main(String[] args) {
//    try {
//        int a=1/0;
//
//    }finally {
//        System.out.println("a");
//        return ;
//    }
//}
//    public static void main(String[] args) {
//        //可以不捕获异常
//        /***
//         *
//
//        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread t, Throwable e) {
//                System.out.println("哈哈哈哈");
//            }
//        });
//         */
//        try {
//            int a=1/0;
//
//        }catch (Exception e){
//            e.printStackTrace();
//
//        }finally {
//            System.out.println("a");
//            return ;
//        }
//    }

//        public static void main(String[] args) {
//            try {
//                ExecutorService threadPool = Executors.newFixedThreadPool(1);
//                threadPool.submit(()->{
//        //        threadPool.execute(()->{
//                    int a=1/0;
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        public static void main(String[] args) {
            ThreadPoolExecutor executor=new ThreadPoolExecutor(2,2,30, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));
            Future future=executor.submit(()-> {
                System.out.println("哈哈哈哈");
                throw new Exception("出错了");
            });
            try {
                future.get();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
}
