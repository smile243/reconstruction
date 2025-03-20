package concurrent.create;

/**
 * 只能单继承
 *
 * @author yjl
 */
public class way1 {
    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("thread");
        }
    }

    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
    }
}
