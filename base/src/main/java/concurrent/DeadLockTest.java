package concurrent;

/**
 * @author yjl
 */
public class DeadLockTest {
    private static final Object resourceA = new Object();
    private static final Object resourceB = new Object();

    public static void main(String[] args) {
        //创建线程
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceA) {
                    System.out.println(Thread.currentThread() + "getResourceA");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread() + "waiting get sourceB");
                    synchronized (resourceB) {
                        System.out.println(Thread.currentThread() + "get ResourceB");
                    }
                }
            }
        });

        //创建线程
        Thread threadB = new Thread(new Runnable() {

            @Override
            public void run() {
                synchronized (resourceB) {
                    System.out.println(Thread.currentThread() + " get ResourceB");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread() + "waiting get ResourceA");
                    synchronized (resourceA) {
                        System.out.println(Thread.currentThread() + "get ResourceA");
                    }
                }
            }
        });
//        Thread threadB = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                synchronized (resourceA) {
//                    System.out.println(Thread.currentThread() + " get ResourceA");
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(Thread.currentThread() + "waiting get ResourceB");
//                    synchronized (resourceB) {
//                        System.out.println(Thread.currentThread() + "get ResourceB");
//                    }
//                }
//            }
//        });
        //启动线程
        threadA.start();
        threadB.start();
    }
}
