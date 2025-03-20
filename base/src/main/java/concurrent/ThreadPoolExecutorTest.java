package concurrent;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 线程dump寻找轮询的原因
 *
 * @author yjl
 */
public class ThreadPoolExecutorTest {
    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(2,
            3,
            30,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>(2),
            new MyThreadFactory("yjl"),
            new ThreadPoolExecutor.DiscardPolicy());

        /**
         * 每隔两秒打印线程池的信息
         */
//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
//        scheduledExecutorService.scheduleAtFixedRate(() -> {
//            System.out.println("=====================================thread-pool-info:" + new Date() + "=====================================");
//            System.out.println("CorePoolSize:" + executorService.getCorePoolSize());
//            System.out.println("PoolSize:" + executorService.getPoolSize());
//            System.out.println("ActiveCount:" + executorService.getActiveCount());
//            System.out.println("KeepAliveTime:" + executorService.getKeepAliveTime(TimeUnit.SECONDS));
//            System.out.println("QueueSize:" + executorService.getQueue().size());
//        }, 0, 2, TimeUnit.SECONDS);
//
//        try {
//            //同时提交5个任务,模拟达到最大线程数
//            for (int i = 0; i < 5; i++) {
//                executorService.execute(new Task());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //休眠10秒，打印日志，观察线程池状态
//        Thread.sleep(10000);
//
//        //每隔3秒提交一个任务,这里线程池按照轮询规则指定哪个线程去执行任务
//        while (true) {
//            Thread.sleep(3000);
//            executorService.submit(new Task());
//        }

        /**
         * 测试线程工作原理
         */
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Task());
        }
        Thread.sleep(1001);
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Task());
        }

        executorService.shutdown();
    }


    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "-执行任务");
        }
    }

    /***
     * 自定义线程工厂
     */
    static class MyThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger no = new AtomicInteger();
        private final String namePrefix;

        public MyThreadFactory(String namePrefix) {
            group = Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + no.getAndIncrement());
            if (t.isDaemon()) {
                t.setDaemon(true);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    System.out.println("handle exception");
                }
            });
            return t;
        }
    }
}




