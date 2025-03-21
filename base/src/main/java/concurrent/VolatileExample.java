package concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @author yjl
 * @since 2025/2/17
 */
public class VolatileExample {
    //没有加volatile,主线程不能检测到子线程对变量的变更
    private static boolean flag = false;
    //以下两个骚操作能让程序正常结束，但是正规的还是flag前加volatile 详见https://mp.weixin.qq.com/s/ryejaMGUrjqqIU-0_fxmqA
    private static int i = 0;
    //private volatile static int i = 0;
    //private static Integer i = 0;
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                flag = true;
                System.out.println("flag 被修改成 true");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        while (!flag) {
            i++;
        }

        System.out.println("程序结束,i=" + i);
    }
}
