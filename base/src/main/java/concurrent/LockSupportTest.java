package concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * 与notify和wait相比,notify是随机唤醒线程，unpark可以指定唤醒哪个(通过传参)；wait和notify必须先获得锁对象，这里的不需要;
 * 推荐使用park(Object blocker)
 *
 * @author yjl
 */
public class LockSupportTest {
    public static void main(String[] args) throws InterruptedException {
//        Thread thread=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("child begin park");
//                LockSupport.park();
//                System.out.println("child unpark");
//            }
//        });
//        thread.start();
//
//        Thread.sleep(1000);
//        System.out.println("main begin unpark");
//        //主线程执行unpark让子线程持有许可证
//        LockSupport.unpark(thread);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("child begin park");
                while (!Thread.currentThread().isInterrupted()) {
                    LockSupport.park(this);
                }
                System.out.println("child unpark");
            }
        });

        thread.start();
        System.out.println("sleep begin");
        Thread.sleep(1000);
        thread.interrupt();
        /**
         * 即使unpark也不会返回，中断才后会返回
         */
//        LockSupport.unpark(thread);
    }
}
