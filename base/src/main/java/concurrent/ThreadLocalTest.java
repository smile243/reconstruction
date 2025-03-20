package concurrent;

/**
 * ThreadLocal不用的时候最好在最后调用remove
 * 使用场景:
 * 在spring事务中，保证一个线程下，一个事务的多个操作拿到的是一个Connection。
 * 在hibernate中管理session。
 * 在JDK8之前，为了解决SimpleDateFormat的线程安全问题。
 * 获取当前登录用户上下文。
 * 临时保存权限数据。
 * 使用MDC保存日志信息。
 *
 * @author yjl
 */
public class ThreadLocalTest {
    private static final ThreadLocal<String> THREADLOCAL = new ThreadLocal<>();
    private static final ThreadLocal<String> THREADLOCALB = new ThreadLocal<>();

    public static void main(String[] args) {
        //1.使用方法，每个线程的本地变量是保存在调用线程的threadLocals变量中的
//        try {
//            THREADLOCAL.set("hello");
//
//            new Thread(() -> {
//                THREADLOCAL.set("thread1");
//                THREADLOCALB.set("thread1");
//                System.out.println("thread1:" + THREADLOCAL.get());
//                System.out.println("thread1:" + THREADLOCALB.get());
//            }).start();
//
//            new Thread(() -> {
//                THREADLOCAL.set("thread2");
//                System.out.println("thread2:" + THREADLOCAL.get());
//            }).start();
//
//            System.out.println(THREADLOCAL.get());
//        }finally {
//            THREADLOCAL.remove();
//        }
        //2.父子线程,子线程可以拿到
        InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
        threadLocal.set("a");
        System.out.println("父线程获取数据：" + threadLocal.get());
        new Thread(() -> {
            System.out.println("子线程获取父线程设置的本地数据：" + threadLocal.get());
        }).start();
        //3.线程池
//        InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        threadLocal.set(6);
//        /***
//         * 由于InheritableThreadLocal在线程初始化的时候才会拷贝内容，而这里共用一个线程池，所以会失效
//         */
//        executorService.submit(() -> {
//            System.out.println("第一次从线程池中获取数据：" + threadLocal.get());
//        });
//        threadLocal.set(7);
//        executorService.submit(() -> {
//            System.out.println("第二次从线程池中获取数据：" + threadLocal.get());
//        });
//        TransmittableThreadLocal<Integer> threadLocal = new TransmittableThreadLocal<>();
//        threadLocal.set(6);
//        System.out.println("父线程获取数据：" + threadLocal.get());
//
//        ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(1));
//
//        threadLocal.set(6);
//        ttlExecutorService.submit(() -> {
//            System.out.println("第一次从线程池中获取数据：" + threadLocal.get());
//        });
//
//        threadLocal.set(7);
//        ttlExecutorService.submit(() -> {
//            System.out.println("第二次从线程池中获取数据：" + threadLocal.get());
//        });


    }
}
