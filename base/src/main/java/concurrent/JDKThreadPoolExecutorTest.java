package concurrent;

import java.util.concurrent.CompletableFuture;


/**
 * Future异步函数编程
 * TEST4的异步是假异步，如果任务还在执行，还是会有阻塞等待的
 *
 * @author yjl
 */
public class JDKThreadPoolExecutorTest {
    public static void main(String[] args) throws Exception {
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));
        /**TEST1
         *execute(Runnable command)方法。没有返回值
         */
//        executor.execute(() -> {
//            System.out.println("关注why技术");
//        });
        /**TEST2
         *有三种submit，但是照提交任务的类型来算分为两个类型
         * Runnable
         * Callable
         */
//        Future<String> future = executor.submit(() -> {
//            System.out.println("关注why技术");
//            return "这次一定！";
//        });
//        System.out.println("future的内容:" + future.get());

        /***TEST3
         *future.get() 方法的返回值为 null,屌用没有，不如execute
         */
//        Future<?> future = executor.submit(() -> {
//            System.out.println("关注why技术");
//        });
//        System.out.println("future的内容:" + future.get());
        /***TEST4
         *
         */
//        AtomicInteger atomicInteger = new AtomicInteger();
//        Future<AtomicInteger> future = executor.submit(() -> {
//            System.out.println("关注why技术");
//            //在这里进行计算逻辑
//            atomicInteger.set(5201314);
//        }, atomicInteger);
//        System.out.println("future的内容:" + future.get());
        /***
         * TEST5
         * google异步回调机制实现真正的异步非阻塞
         */
//        ListeningExecutorService listeningExecutor= MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
//        ListenableFuture listenableFuture=listeningExecutor.submit(()->{
//            System.out.println(Thread.currentThread().getName()+"-女神：我开始化妆了，好了我叫你");
//            TimeUnit.SECONDS.sleep(5);
//            //方式二
//            throw new Exception("男神约我看电影，就不和你吃饭了。");
//            //方式一
//            //return "化妆完毕";
//        });
//        //方式一 addListener
//        listenableFuture.addListener(()->{
//            try {
//                System.out.println(Thread.currentThread().getName()+"-future的内容:"+listenableFuture.get());
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        },executor);
//        //方式二 更优雅
//        Futures.addCallback(listenableFuture, new FutureCallback<String>() {
//
//            @Override
//            public void onSuccess(@Nullable String s) {
//                System.out.println(Thread.currentThread().getName()+"-future的内容:"+s);
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                System.out.println(Thread.currentThread().getName()+"-女神放你鸽子了");
//                throwable.printStackTrace();
//
//            }
//        });
//        System.out.println(Thread.currentThread().getName()+"-等女神化妆的时候可以干点自己的事情");
//        Thread.currentThread().join();
        /***
         * TEST6 CompletableFutrue: JDK8自带的真正的异步编程
         */
//        CompletableFuture<String> completableFuture=CompletableFuture.supplyAsync(()->{
//            System.out.println(Thread.currentThread().getName()+"-女神：我开始化妆了，好了我叫你");
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            }catch (InterruptedException  e){
//                e.printStackTrace();
//            }
//            return "化妆完毕了";
//        });
//        completableFuture.whenComplete((returnstr,exeception)->{
//            if(exeception==null){
//                System.out.println(Thread.currentThread().getName()+returnstr);
//            }else{
//                System.out.println(Thread.currentThread().getName()+"女神放你鸽子了");
//                exeception.printStackTrace();
//            }
//        });
//        System.out.println(Thread.currentThread().getName() + "-等女神化妆的时候可以干点自己的事情");
        /***
         * TEST7 CompletableFutrue处理异常
         */
        CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "-女神：我开始化妆了，好了我叫你");
            //return "我化好了";
            throw new RuntimeException("男神约我看电影了，我们下次再约吧，你是个好人");
        }).handleAsync((result, exception) -> {
            if (exception != null) {
                System.out.println(Thread.currentThread().getName() + "-女神放你鸽子了");
                return exception.getCause();
            } else {
                return result;
            }
        }).thenApplyAsync((returnstr) -> {
            System.out.println(Thread.currentThread().getName() + "-" + returnstr);
            return returnstr;
        });
        System.out.println(Thread.currentThread().getName() + "-等女神化妆的时候可以干点自己的事情");
        Thread.currentThread().join();
    }
}
