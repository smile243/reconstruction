package concurrent;


/**
 * 监视器锁通过synchronized获得,wait释放的只是自己()内作用的对象
 * notify和notifyAll只会唤醒之前阻塞的线程,被唤醒的线程需要竞争到锁才能执行
 *
 * @author yjl
 */
public class notifyAndWaitTest {
    private static volatile Object resourceA = new Object();
    private static volatile Object resourceB = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //A上锁
                    synchronized (resourceA) {
                        System.out.println("threadA get resourceA lock");
                        //B上锁
                        synchronized (resourceB) {
                            System.out.println("threadA get resourceB lock");

                            System.out.println("threadA release resourceA lock");
                            //释放锁A,阻塞挂起threadA线程
                            resourceA.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //让A先获取锁
                    Thread.sleep(1000);
                    synchronized (resourceA) {
                        System.out.println("threadB get resourceA lock");

                        System.out.println("threadB try get resourceB lock。。。。");
                        synchronized (resourceB) {
                            System.out.println("threadB get resourceB lock");

                            System.out.println("threadB release resourceA lock");
                            resourceA.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

        System.out.println("over");

//        Thread threadA =new Thread(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (resourceA){
//                    System.out.println("threadA  get resourceA lock");
//                    try {
//                        System.out.println("A begin wait");
//                        resourceA.wait();
//                        System.out.println("A end wait");
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        Thread threadB =new Thread(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (resourceA){
//                    System.out.println("threadB  get resourceA lock");
//                    try {
//                        System.out.println("B begin wait");
//                        resourceA.wait();
//                        System.out.println("B end wait");
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        Thread threadC =new Thread(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (resourceA){
//                    System.out.println("C  begin notify");
//                        //resourceA.notify();
//                    resourceA.notifyAll();
//                }
//            }
//        });
//        threadA.start();
//        threadB.start();
//        //确保A,B都执行到wait方法
//        Thread.sleep(1000);
//        threadC.start();
//        //主线程等待以下几个线程执行结束;
//        threadA.join();
//        threadB.join();
//        threadC.join();
//
//        System.out.println("over");
    }


}
