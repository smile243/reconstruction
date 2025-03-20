package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yjl
 */
public class ConditionWaitTest implements Runnable {
    private Lock lock;

    private Condition condition;

    public ConditionWaitTest(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            System.out.println("begin - wait");
            //阻塞线程,内部1将线程加入到Condition队列中，2释放锁
            condition.await();
            System.out.println("end - wait");
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        // 保证两个线程获取的是同一把锁 和 同一个Condition

        new Thread(new ConditionWaitTest(lock, condition)).start();
        new Thread(new ConditionNotifyTest(lock, condition)).start();
    }
}
