package concurrent.create;

/**
 * 无返回值
 *
 * @author yjl
 */
public class way2 {
    public static class RunableTask implements Runnable {
        @Override
        public void run() {
            System.out.println("thread");
        }
    }

    public static void main(String[] args) {
        RunableTask task = new RunableTask();
        new Thread(task).start();
    }
}
