package concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 动态更新线程池
 *
 * @author yjl
 */
public class ThreadChangeDemo {
    public static void main(String[] args) throws InterruptedException {
//    dynamicModifyExecutor();
        BlockingQueue blockingQueue = new LinkedBlockingQueue(5);
        for (int i = 0; i < 10; i++) {
            System.out.println("开始put" + i);
            blockingQueue.put(i);
            System.out.println("put" + i + "成功" + "剩余" + blockingQueue.remainingCapacity());
        }
    }

    /***
     * 自定义线程池
     * @return
     */
    private static ThreadPoolExecutor buildThreadPoolExecutor() {
        return new ThreadPoolExecutor(2,
            5,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10),
            new ThreadPoolExecutorTest.MyThreadFactory("yjl"));
    }

    private static void dynamicModifyExecutor() throws InterruptedException {
        ThreadPoolExecutor executor = buildThreadPoolExecutor();
        for (int i = 0; i < 15; i++) {
            executor.submit(() -> {
                threadPoolStatus(executor, "创建任务");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPoolStatus(executor, "改变前");
        TimeUnit.SECONDS.sleep(1);
        executor.setCorePoolSize(10);
        //活动线程数最大值和maxpoolsize相关，具体见源码的getTask
        executor.setMaximumPoolSize(10);
        //控制活动线程
        //executor.prestartCoreThread();
        threadPoolStatus(executor, "改变后");
        Thread.currentThread().join();
        executor.shutdown();
    }

    /***
     * 打印线程池状态
     */
    private static void threadPoolStatus(ThreadPoolExecutor executor, String name) {
        LinkedBlockingQueue queue = (LinkedBlockingQueue) executor.getQueue();
        System.out.println(Thread.currentThread().getName() + "-" + name + "-:" +
            "核心线程数:" + executor.getCorePoolSize() +
            "活动线程数" + executor.getActiveCount() +
            "最大线程数" + executor.getMaximumPoolSize() +
            "线程活跃度" + divide(executor.getActiveCount(), executor.getMaximumPoolSize()) +
            "任务完成数" + executor.getCompletedTaskCount() +
            "队列大小" + (queue.size() + queue.remainingCapacity()) +
            "当前排队线程数" + queue.size() +
            "队列剩余大小" + queue.remainingCapacity() +
            "队列实用度" + divide(queue.size(), queue.size() + queue.remainingCapacity()));
    }

    private static String divide(int n1, int n2) {
        return String.format("%1.2f%%", Double.parseDouble(n1 + "") / Double.parseDouble(n2 + "") * 100);
    }

}
