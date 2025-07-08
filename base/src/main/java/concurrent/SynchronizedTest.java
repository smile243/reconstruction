package concurrent;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedTest {
    private final Object o = new Object();
    public void hello(){
        System.out.println("进入方法");
        //锁代码块可以实现更加细粒度的锁
        synchronized (o){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("进入同步代码块");
        }
        System.out.println("方法执行结束");
    }
    public static void main(String[] args) {
        SynchronizedTest SynchronizedTest = new SynchronizedTest();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executorService.execute(SynchronizedTest::hello);
        }
        executorService.shutdown();
    }
}
