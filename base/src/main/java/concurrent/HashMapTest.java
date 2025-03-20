package concurrent;

import java.util.HashMap;

/**
 * @author yjl
 */
public class HashMapTest {
    public class MyService {
        public HashMap map = new HashMap();
    }

    public class Thread1A extends Thread {
        private MyService service;

        public Thread1A(MyService service) {
            super();
            this.service = service;
        }

        @Override
        public void run() {
            for (int i = 0; i < 50000; i++) {
                service.map.put("ThreadA" + (i + 1), "ThreadA" + i + 1);
                System.out.println("ThreadA" + (i + 1));
            }
        }

    }

    public class Thread1B extends Thread {
        private MyService service;

        public Thread1B(MyService service) {
            super();
            this.service = service;
        }

        @Override
        public void run() {
            for (int i = 0; i < 50000; i++) {
                service.map.put("ThreadB" + (i + 1), "ThreadB" + i + 1);
                System.out.println("ThreadB" + (i + 1));
            }
        }
    }

    public static void main(String[] args) {
        HashMapTest test = new HashMapTest();
        MyService service = test.new MyService();
        Thread1A a = test.new Thread1A(service);
        Thread1B b = test.new Thread1B(service);
        Thread1A c = test.new Thread1A(service);
        Thread1B d = test.new Thread1B(service);
        Thread1A e = test.new Thread1A(service);
        Thread1B f = test.new Thread1B(service);
        Thread1A g = test.new Thread1A(service);
        Thread1B h = test.new Thread1B(service);
        a.start();
        b.start();
        c.start();
        d.start();
        e.start();
        f.start();
        g.start();
        h.start();
    }

}
