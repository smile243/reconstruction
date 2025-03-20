package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 使用Lock锁+condition唤醒
 *
 * @author yjl
 */
public class ConditionNotifyTest implements Runnable {
    private Lock lock;

    private Condition condition;

    public ConditionNotifyTest(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            System.out.println("begin - notify");
            //唤醒线程;内部   1将Condition队列中的线程加入到AQS队列  2 如果没有线程占用锁，就唤醒该线程
            condition.signal();
            System.out.println("end - notify");
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }
}
