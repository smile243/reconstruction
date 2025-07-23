package concurrent;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 有3个线程，线程名分别为A、B、C。
 * A线程随机生成一个 0 - 100 之间的整数，仅生成一次，例如20。
 * 线程名B、C进行猜数，并通知线程A，线程A会根据猜测的结果来通知B、C线程是猜大了、猜小了、还是猜正确了。
 * 猜数的线程B、C依据线程A给的提示继续猜数，如此循环。猜中以后，由A线程输出猜中的线程，并且三个线程都中止运行。
 * 请注意：
 * 1. 线程B和C需要等线程A生成猜数结果后，才开始猜数
 * 2. 线程B和C任意哪个线程都可以先猜数，但两个线程需要轮流猜数
 * @author: yjl
 */
public class ThreadGuess {
    private final Lock lock = new ReentrantLock();
    private final Condition cond = lock.newCondition();

    private boolean finish = false;
    private volatile long threadId = -1L;
    private volatile long lastThreadId = -1L;
    private volatile int guess = -1;
    private volatile int compare = -1;

    public void work() {
        boolean greater = false;
        int currentGuess = -1;
        while (!finish) {
            lock.lock();
            if (threadId != -1L || lastThreadId == Thread.currentThread().getId()) {
                try {
                    cond.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            threadId = Thread.currentThread().getId();
            lastThreadId = threadId;
            if (currentGuess == -1) {
                guess = new Random().nextInt(100) + 1;
            } else if (greater) {
                guess = new Random().nextInt(currentGuess) + 1;
            } else {
                guess = new Random().nextInt(100 - currentGuess) + currentGuess;
            }
            System.out.println("线程" + threadId + "当前猜测的数是:" + guess);
            currentGuess = guess;
            cond.signalAll();
            try {
                cond.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            greater = compare > 0;
            threadId = -1L;
            guess = -1;
            cond.signalAll();
            lock.unlock();
        }
    }

    public void start() {
        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                work();
            }
        });
        Thread c = new Thread(new Runnable() {
            @Override
            public void run() {
                work();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                int answer = new Random().nextInt(100) + 1;
                System.out.println("准确答案是: " + answer);
                b.start();
                c.start();
                while (true) {
                    lock.lock();
                    if (guess == -1) {
                        try {
                            cond.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    compare = guess - answer;
                    if (compare == 0) {
                        finish = true;
                        cond.signalAll();
                        lock.unlock();
                        if (b.getId() == threadId) {
                            System.out.println("B guess correctly!");
                        } else {
                            System.out.println("C guess correctly!");
                        }
                        break;
                    }
                    cond.signalAll();
                    lock.unlock();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new ThreadGuess().start();
    }
}
