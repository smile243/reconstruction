package concurrent;

import java.util.concurrent.TimeUnit;

/**
 * Integer的--会导致对象改变,拆包
 * 使用concurrentHashMap解决
 *
 * @author yjl
 */
public class SynchronizedTest {

    public static void main(String[] args) {
        Thread why = new Thread(new TicketConsumer(10), "why");
        Thread mx = new Thread(new TicketConsumer(10), "mx");
        why.start();
        mx.start();
    }
}

class TicketConsumer implements Runnable {

    private volatile static Integer ticket;

    public TicketConsumer(int ticket) {
        this.ticket = ticket;
    }

    //private final Interner<Integer> interner=Interners.newWeakInterner();
    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + "开始抢第" + ticket + "张票，对象加锁之前：" + System.identityHashCode(ticket));
            //synchronized (interner.intern(ticket)) {
            synchronized (ticket) {
                System.out.println(Thread.currentThread().getName() + "抢到第" + ticket + "张票，成功锁到的对象：" + System.identityHashCode(ticket));
                if (ticket > 0) {
                    try {
                        //模拟抢票延迟
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "抢到了第" + ticket-- + "张票，票数减一");
                } else {
                    return;
                }
            }
        }
    }
}

