package concurrent;

/**
 * 如果当前进程不存在用户线程，但是还存在执行中的守护线程，jvm会直接结束
 *
 * @author yjl
 */
public class DaemonThread {
    public static void main(String[] args) {
        Thread daemonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {

                }
            }
        });
        daemonThread.setDaemon(true);
        daemonThread.start();
    }
}
