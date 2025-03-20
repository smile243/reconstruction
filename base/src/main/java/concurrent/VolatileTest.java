package concurrent;

public class VolatileTest {
    /***
     * 不保证原子性
     * 原因是number++在字节码层级被分割为三个指令，当将工作内存的值写回主存时，并发会出现覆盖问题
     * 可以 1在方法上使用synchronized 2加lock锁 3用AtomicInteger原子类代替
     */
    volatile int number = 0;

    public void add() {
        number++;
    }

    public static void main(String[] args) {
        VolatileTest test = new VolatileTest();
        for (int j = 0; j < 20; j++) {
            new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                    test.add();
                }
            }, String.valueOf(j)).start();
        }
        //为了得到多线程下最终运行的结果
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + "\tfinal number result = " + test.number);
    }

}
