package concurrent;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArraySet的底层是CopyOnWriteArrayList
 *
 * @author yjl
 */
public class CopyOnWriteArrayListTest {
    private static volatile CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        arrayList.add("zs");
        arrayList.add("ls");
        arrayList.add("ww");
        arrayList.add("wemz");

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                arrayList.set(1, "yjl");
                arrayList.remove(3);
                arrayList.remove(2);
            }
        });
        //线程启动前获取快照
        Iterator<String> itr = arrayList.iterator();
        threadOne.start();
        threadOne.join();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

        System.out.println(Arrays.toString(arrayList.toArray()));
    }
}
