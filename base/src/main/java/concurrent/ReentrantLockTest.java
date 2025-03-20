package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 里面的Sync继承AQS
 * 默认非公平锁，公平锁通过hasQueuedPredecessors实现
 * state代表可重入次数
 * 独占锁，同一时刻只能有一个线程获取锁，效率低
 *
 * @author yjl
 */
public class ReentrantLockTest {
    private static volatile Lock lock = new ReentrantLock();

    public static class ReentranLockList {
        private List<String> list = new ArrayList<>();

        public void add(String e) {
            lock.lock();
            try {
                list.add(e);
            } finally {
                lock.unlock();
            }
        }

        public void remove(String e) {
            lock.lock();
            try {
                list.remove(e);
            } finally {
                lock.unlock();
            }
        }

        public String get(int index) {
            lock.lock();
            try {
                return list.get(index);
            } finally {
                lock.unlock();
            }
        }
    }

    public void testA() {
        lock.lock();
        try {
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockTest test = new ReentrantLockTest();
        test.testA();
    }

}
