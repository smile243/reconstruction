package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * signal和await调用前必须获取条件变量对应的锁
 *
 * @author yjl
 */
public class ConditionVariableTest {
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        //可创建多个条件变量，这是与synchronized+wait+notify的区别
        Condition condition = lock.newCondition();

        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("begin wait");
                    condition.await();
                    System.out.println("end wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("begin signal");
                    condition.signal();
                    System.out.println("end signal");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

        System.out.println("over");
    }
}
