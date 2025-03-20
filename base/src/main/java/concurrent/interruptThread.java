package concurrent;

/**
 * 通过设置线程的中断标志并不能直接终止该线程的执行,而是被中断的线程根据中断状态自行处理
 *
 * @author yjl
 */
public class interruptThread {
    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {

                }
            }
        });
        one.start();
        one.interrupt();
        System.out.println("interrupt[one] " + one.isInterrupted());
        //interrupted方法获取的是当前线程的中断状态，不是调用者的
        System.out.println("interrupt[one] " + one.interrupted());
        System.out.println("interrupt " + Thread.interrupted());

        System.out.println("interrupt[one] " + one.isInterrupted());
        one.join();
        System.out.println("over");
    }
}
